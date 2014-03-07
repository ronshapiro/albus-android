package me.ronshapiro.albus.android;

import org.json.JSONObject;

/**
 * An interface to abstract the storage mechanism of data that will later be sent to the Albus
 * server.
 */
public interface Storage {

    /**
     * Serializes report data to later be retrieved when it is ready to be sent to the server.
     * {@code write()}.
     *
     * @param id a key to distinguish instance reports.
     * @param json data to store
     */
    void write(long id, JSONObject json);

    /**
     * The inverse of {@link #write(long, JSONObject)}, this deserializes the data so that it may be
     * sent to the Albus server.
     *
     * @param id key to retrieve report data
     * @return data to send to Albus server
     */
    JSONObject read(long id);

    /**
     * @param id key to a specific report's data that is ready to be removed; it has been sent
     * successfully to the server.
     */
    void purge(long id);

    /**
     * @return the oldest, non-deleted, id that must be sent to the server
     */
    long getOldestId();

    /**
     * @return the most recently recorded id
     */
    long getNewestId();
}
