package me.ronshapiro.albus.android.api;

import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Manages over-the-wire protocol with the Albus service. All calls should be run synchronously and
 * do no interfacing with the app.
 *
 * @see AlbusApiClient
 */
/* package */ class AlbusApi {

    // TODO make mPrefixed variables consistent
    private OkHttpClient mHttpClient;
    private final String mBaseUrl;
    private static final String METHOD_POST = "POST";

    /* package */ AlbusApi(OkHttpClient client) {
        mHttpClient = client;
        mBaseUrl = "https://some.albus.server.com";
    }

    /* package */ AlbusApi(OkHttpClient client, String baseUrl) {
        mHttpClient = client;
        mBaseUrl = baseUrl;
    }

    /* package */ void postData(JSONArray body) throws IOException {
        HttpURLConnection connection = mHttpClient.open(new URL(mBaseUrl + "/data"));
        DataOutputStream bodyStream = null;
        try {
            connection.setRequestMethod(METHOD_POST);
            connection.setDoOutput(true);
            bodyStream = new DataOutputStream(connection.getOutputStream());
            bodyStream.writeBytes(body.toString());
            bodyStream.close();

            // TODO set timeout
            // TODO authenticate user

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new ApiException(responseCode + " received for post to " +
                        connection.getURL().toExternalForm());
            }
        } finally {
            if (bodyStream != null) bodyStream.close();
        }
    }

}
