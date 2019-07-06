package com.peaches.epicskyblock;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class NMSUtils {

    public static void sendBorder(Player p, double x, double z, double radius) {
        //TODO
        try {
            Class<?> worldborder = getNMSClass("WorldBorder");
            Object worldborderinstance = getNMSClass("WorldBorder").newInstance();

            worldborder.getMethod("setCenter", double.class, double.class).invoke(worldborderinstance, x, z);
            worldborder.getMethod("setSize", double.class).invoke(worldborderinstance, radius * 2);

            Object enumBorder = getNMSClass("PacketPlayOutWorldBorder").getDeclaredClasses()[0].getField("INITIALIZE").get(null);
            Constructor<?> worldborderconstructor = getNMSClass("PacketPlayOutWorldBorder").getConstructor(getNMSClass("WorldBorder"), getNMSClass("PacketPlayOutWorldBorder").getDeclaredClasses()[0]);
            Object worldborderPacket = worldborderconstructor.newInstance(worldborderinstance, enumBorder);
            sendPacket(p, worldborderPacket);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void sendActionBar(Player player, String message) {
        try {
            Constructor<?> constructor = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), byte.class);

            Object text = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, ChatColor.translateAlternateColorCodes('&', "{\"text\":\"" + message + "\"}"));
            Object packet = constructor.newInstance(text, (byte) 2);
            sendPacket(player, packet);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static void sendSubTitle(Player player, String message, int fadeIn, int displayTime, int fadeOut) {
        try {
            Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
            Object chat = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, ChatColor.translateAlternateColorCodes('&', "{\"text\":\"" + message + "\"}"));

            Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);
            Object packet = titleConstructor.newInstance(enumTitle, chat, fadeIn, displayTime, fadeOut);

            sendPacket(player, packet);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static void sendTitle(Player player, String message, int fadeIn, int displayTime, int fadeOut) {
        try {
            Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
            Object chat = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, ChatColor.translateAlternateColorCodes('&', "{\"text\":\"" + message + "\"}"));

            Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);
            Object packet = titleConstructor.newInstance(enumTitle, chat, fadeIn, displayTime, fadeOut);

            sendPacket(player, packet);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    public static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getNMSClass(String name) {
        String version = EpicSkyblock.getInstance().getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
