package me.ronshapiro.albus.android.api;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import me.ronshapiro.albus.android.Storage;

public class AlbusApiClient {

    private static final String TAG = AlbusApiClient.class.getSimpleName();
    private final AlbusApi mApi;
    private ExecutorService mExecutor;

    public AlbusApiClient() {
        mApi = new AlbusApi(new OkHttpClient());
        init();
    }

    /* visible for testing */
    public AlbusApiClient(AlbusApi api) {
        mApi = api;
        init();
    }

    private void init() {
        mExecutor = Executors.newSingleThreadExecutor();
    }

    public Future<?> postDataAsync(final Storage storage) {
        return mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                JSONArray data = new JSONArray();
                for (long id = storage.getOldestId(); id <= storage.getNewestId(); id++) {
                    data.put(storage.read(id));
                }

                boolean success = false;
                try {
                    mApi.postData(data);
                    success = true;
                } catch (IOException e) {
                    // do nothing, the data will not be removed from storage and it will be
                    // attempted again next time
                    Log.i(TAG, "Exception thrown while attempting to connect to Albus server", e);
                }

                if (success) {
                    for (long id = storage.getOldestId(); id <= storage.getNewestId(); id++) {
                        storage.purge(id);
                    }
                }
            }
        });
    }
}
