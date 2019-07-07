package com.peaches.epicskyblock;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static ItemStack makeItem(Material material, int amount, int type, String name) {
        ItemStack item = new ItemStack(material, amount, (short) type);
        ItemMeta m = item.getItemMeta();
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack makeItem(Material material, int amount, int type, String name, List<String> lore) {
        ItemStack item = new ItemStack(material, amount, (short) type);
        ItemMeta m = item.getItemMeta();
        m.setLore(lore);
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(m);
        return item;
    }

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> color(List<String> strings) {
        return strings.stream().map(Utils::color).collect(Collectors.toList());
    }
}
