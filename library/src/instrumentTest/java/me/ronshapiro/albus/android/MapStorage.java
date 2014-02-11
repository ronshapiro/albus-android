package me.ronshapiro.albus.android;

import org.json.JSONObject;

import java.util.Map;

/**
 * Don't use this in production! This doesn't persist any data and won't provide any practical use
 * beyond testing.
 */
public class MapStorage implements Storage {

    private Map<Long, JSONObject> map;
    private long oldest = 0;
    private long newest = 0;

    MapStorage(Map<Long, JSONObject> map) {
        this.map = map;
    }

    @Override
    public void write(long id, JSONObject json) {
        map.put(id, json);
        newest = id;
    }

    @Override
    public JSONObject read(long id) {
        return map.get(id);
    }

    @Override
    public void purge(long id) {
        map.remove(id);
        oldest++;
    }

    @Override
    public long getOldestId() {
        return oldest;
    }

    @Override
    public long getNewestId() {
        return newest;
    }
}
