package me.ronshapiro.albus.android;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class SharedPreferencesStorage implements Storage {

    private SharedPreferences sharedPreferences;
    private static final String NO_DATA_FOUND = "{\"error\": \"no data found\"}";
    private static final String KEY_OLDEST_ID = "oldest-id";
    private static final String KEY_NEWEST_ID = "newest-id";

    public SharedPreferencesStorage(Context context, String fileName) {
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    @Override
    public void write(long id, JSONObject json) {
        sharedPreferences.edit()
                .putString(idToKey(id), json.toString())
                .putLong(KEY_NEWEST_ID, id)
                .commit();
    }

    @Override
    public JSONObject read(long id) {
        String data = sharedPreferences.getString(idToKey(id), NO_DATA_FOUND);
        try {
            return new JSONObject(data);
        } catch (JSONException e) {
            throw new RuntimeException("Deserialized data was not in JSON format: " + data, e);
        }
    }

    @Override
    public void purge(long id) {
        sharedPreferences.edit()
                .remove(idToKey(id))
                .putLong(KEY_OLDEST_ID, id + 1)
                .commit();
    }

    @Override
    public long getOldestId() {
        return sharedPreferences.getLong(KEY_OLDEST_ID, 0l);
    }

    @Override
    public long getNewestId() {
        return sharedPreferences.getLong(KEY_NEWEST_ID, 0l);
    }

    private String idToKey(long id) {
        return String.valueOf(id);
    }
}
