package com.peaches.epicskyblock.serializer.typeadapter;

import com.google.gson.*;
import com.peaches.epicskyblock.EpicSkyblock;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import java.lang.reflect.Type;
import java.util.logging.Level;

public class ChunkTypeAdapter implements JsonSerializer<Chunk>, JsonDeserializer<Chunk> {

    @Override
    public JsonElement serialize(Chunk chunk, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        try {
            object.add("x", new JsonPrimitive(chunk.getX()));
            object.add("z", new JsonPrimitive(chunk.getZ()));
            object.add("world", new JsonPrimitive(chunk.getWorld().getName()));
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
            EpicSkyblock.getInstance().getLogger().log(Level.WARNING, "Error encountered while serializing a Location.");
            return object;
        }
    }


    @Override
    public Chunk deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        JsonObject object = jsonElement.getAsJsonObject();
        try {

            return Bukkit.getWorld(object.get("world").getAsString()).getChunkAt(object.get("x").getAsInt(), object.get("z").getAsInt());
        } catch (Exception ex) {
            ex.printStackTrace();
            EpicSkyblock.getInstance().getLogger().log(Level.WARNING, "Error encountered while" +
                    " deserializing a Location.");
            return null;
        }


    }
}