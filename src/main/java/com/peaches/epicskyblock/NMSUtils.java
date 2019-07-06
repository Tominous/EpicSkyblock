package com.peaches.epicskyblock;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NMSUtils {

    public static void sendWorldBorder(Player player, Color color, double size, Location centerLocation) {
        try {
            if (size % 2 == 1) size++;

            Object worldBorder = getNMSClass("WorldBorder").getConstructor().newInstance();
            Object craftWorld = getCraftClass("CraftWorld").cast(centerLocation.getWorld());

            setField(worldBorder, "world", craftWorld.getClass().getMethod("getHandle").invoke(craftWorld), false);

            worldBorder.getClass().getMethod("setCenter", double.class, double.class).invoke(worldBorder, centerLocation.getBlockX(), centerLocation.getBlockZ());

            worldBorder.getClass().getMethod("setSize", double.class).invoke(worldBorder, size);

            worldBorder.getClass().getMethod("setWarningTime", int.class).invoke(worldBorder, 0);

            if (color == Color.Green) {
                worldBorder.getClass().getMethod("transitionSizeBetween", double.class,
                        double.class, long.class).invoke(worldBorder, size - 0.1D, size, 20000000L);
            } else if (color == Color.Red) {
                worldBorder.getClass().getMethod("transitionSizeBetween", double.class,
                        double.class, long.class).invoke(worldBorder, size, size - 1.0D, 20000000L);
            }

            Object packet = getNMSClass("PacketPlayOutWorldBorder").getConstructor(getNMSClass("WorldBorder"),
                    getNMSClass("PacketPlayOutWorldBorder").getDeclaredClasses()[0]).newInstance(worldBorder,
                    Enum.valueOf((Class<Enum>) getNMSClass("PacketPlayOutWorldBorder").getDeclaredClasses()[0], "INITIALIZE"));
            sendPacket(player, packet);
        } catch (Exception e) {
            EpicSkyblock.getInstance().sendErrorMessage(e);
        }
    }

    public static void sendActionBar(Player player, String message) {
        try {
            Constructor<?> constructor = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), byte.class);

            Object text = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, ChatColor.translateAlternateColorCodes('&', "{\"text\":\"" + message + "\"}"));
            Object packet = constructor.newInstance(text, (byte) 2);
            sendPacket(player, packet);
        } catch (Exception e) {
            EpicSkyblock.getInstance().sendErrorMessage(e);
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
        } catch (Exception e) {
            EpicSkyblock.getInstance().sendErrorMessage(e);
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
        } catch (Exception e) {
            EpicSkyblock.getInstance().sendErrorMessage(e);
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

    public static Class<?> getNMSClass(String name) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + getVersion() + "." + name);
    }

    public static Class<?> getCraftClass(String name) throws ClassNotFoundException {
        return Class.forName("org.bukkit.craftbukkit." + getVersion() + "." + name);
    }

    public static void setField(Object object, String fieldName, Object fieldValue, boolean declared) {
        try {
            Field field;

            if (declared) {
                field = object.getClass().getDeclaredField(fieldName);
            } else {
                field = object.getClass().getField(fieldName);
            }

            field.setAccessible(true);
            field.set(object, fieldValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getVersion() {
        return EpicSkyblock.getInstance().getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public enum Color {
        Blue, Green, Red
    }
}