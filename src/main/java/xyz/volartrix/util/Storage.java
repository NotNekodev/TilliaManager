package xyz.volartrix.util;

import java.util.HashMap;

public class Storage {

    private HashMap<String, String> storageMap;

    // Constructor to initialize the storage map
    public Storage() {
        storageMap = new HashMap<>();
    }

    // Method to set a key-value pair
    public void set(String key, String value) {
        storageMap.put(key, value);
    }

    // Method to get the value for a given key
    public String get(String key) {
        // Check if the key exists, if not return a default message
        return storageMap.getOrDefault(key, "ERR_NOTFOUND");
    }

    public void remove(String key) {
        storageMap.remove(key);
    }
}
