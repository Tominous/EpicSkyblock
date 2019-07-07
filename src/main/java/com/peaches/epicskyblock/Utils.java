package com.peaches.epicskyblock;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public static boolean isBlockValuable(Block b) {
        return EpicSkyblock.getConfiguration().blockvalue.containsKey(b.getType()) || b.getState() instanceof CreatureSpawner;
    }

    public static List<Island> getTopIslands() {
        List<Island> islands = new ArrayList<>(EpicSkyblock.getIslandManager().islands.values());
        islands.sort(Comparator.comparingInt(Island::getValue));
        Collections.reverse(islands);
        return islands;
    }


    public static boolean isSafe(Location loc) {
        return (loc.getBlock().getType().equals(Material.AIR)
                && (!loc.clone().add(0, -1, 0).getBlock().getType().equals(Material.AIR) && !loc.clone().add(0, -1, 0).getBlock().isLiquid()));
    }


    public static Location getNewHome(Island island, Location loc) {
        Block b;
        b = EpicSkyblock.getIslandManager().getWorld().getHighestBlockAt(loc);
        if (isSafe(b.getLocation())) {
            return b.getLocation().add(0.5, 1, 0.5);
        }

        for (double X = island.getPos1().getX(); X <= island.getPos2().getX(); X++) {
            for (double Z = island.getPos1().getZ(); Z <= island.getPos2().getZ(); Z++) {
                b = EpicSkyblock.getIslandManager().getWorld().getHighestBlockAt((int) X, (int) Z);
                if (isSafe(b.getLocation())) {
                    return b.getLocation().add(0.5, 1, 0.5);
                }
            }
        }
        return null;
    }
}
