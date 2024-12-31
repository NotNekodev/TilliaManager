package xyz.volartrix.util;

import java.util.HashMap;

public class Storage {

    private final HashMap<String, String> storageMap;

    public Storage() {
        storageMap = new HashMap<>();
    }

    public void set(String key, String value) {
        storageMap.put(key, value);
    }

    public String get(String key) {
        return storageMap.getOrDefault(key, "ERR_NOTFOUND");
    }

    public void remove(String key) {
        storageMap.remove(key);
    }

    public HashMap<String, String> getStorageMap() {
        return storageMap;
    }
}

