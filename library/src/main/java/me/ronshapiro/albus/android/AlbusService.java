package me.ronshapiro.albus.android;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.util.concurrent.ExecutionException;

import static me.ronshapiro.albus.android.BuildConfig.DEBUG;

public class AlbusService extends IntentService {

    private static final String TAG = AlbusService.class.getSimpleName();

    public AlbusService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Albus dumbledore = Albus.get();

        try {
            dumbledore.getApiClient().postDataAsync(dumbledore.getStorage()).get();
        } catch (InterruptedException e) {
            if (DEBUG) Log.e(TAG, "error occurred in postDataAsync:", e);
        } catch (ExecutionException e) {
            if (DEBUG) Log.e(TAG, "error occurred in postDataAsync:", e);
        }
    }

}
