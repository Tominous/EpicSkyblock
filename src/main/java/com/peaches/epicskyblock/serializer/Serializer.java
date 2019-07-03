package com.peaches.epicskyblock.serializer;


import com.peaches.epicskyblock.EpicSkyblock;

public class Serializer {


    /**
     * Saves your class to a .json file.
     */
    public void save(Object instance) {
        EpicSkyblock.getPersist().save(instance);
    }

    /**
     * Loads your class from a json file
     */
    public <T> T load(T def, Class<T> clazz, String name) {
        return EpicSkyblock.getPersist().loadOrSaveDefault(def, clazz, name);
    }


}
