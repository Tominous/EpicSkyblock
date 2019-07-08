package com.peaches.epicskyblock.gui;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.Island;
import com.peaches.epicskyblock.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TopGUI {

    public static Inventory inventory;

    public static int scheduler;

    static {
        inventory = Bukkit.createInventory(null, 27, Utils.color(EpicSkyblock.getConfiguration().TopGUITitle));
        scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(EpicSkyblock.getInstance(), TopGUI::addContent, 0, 20);
    }

    public static void addContent() {
        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 7, " "));
        }
        List<Island> top = Utils.getTopIslands();
        for (int i = 1; i <= 10; i++) {
            if (top.size() >= i) {
                ArrayList<String> lore = new ArrayList<>();
                Island island = top.get(i - 1);
                lore.add("&b&l * &7Leader: &b" + island.getOwner());
                lore.add("&b&l * &7Rank: &b" + i);
                lore.add("&b&l * &7Value: &b" + island.getValue());
                ItemStack head = Utils.makeItem(Material.SKULL_ITEM, 1, 3, "&b&l" + island.getOwner(), Utils.color(lore));
                SkullMeta m = (SkullMeta) head.getItemMeta();
                m.setOwner(island.getOwner());
                head.setItemMeta(m);
                inventory.setItem(EpicSkyblock.getConfiguration().islandTopSlots.get(i), head);
            }
        }
    }
}