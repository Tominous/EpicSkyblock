package com.peaches.epicskyblock.gui;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.Island;
import com.peaches.epicskyblock.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;

public class WarpGUI {

    public Inventory inventory;
    public int islandID;
    public int scheduler;
    public HashMap<Integer, Island.Warp> warps = new HashMap<>();

    public WarpGUI(Island island) {
        this.inventory = Bukkit.createInventory(null, 27, Utils.color(EpicSkyblock.getConfiguration().WarpGUITitle));
        islandID = island.getId();
        scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(EpicSkyblock.getInstance(), this::addContent, 0, 10);
    }

    public void addContent() {
        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 7, " "));
        }
        int i = 9;
        warps.clear();
        for (Island.Warp warp : EpicSkyblock.getIslandManager().islands.get(islandID).getWarps()) {
            warps.put(i, warp);
            inventory.setItem(i, Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 4, Utils.color("&b&l" + warp.getName())));
            i++;
        }
    }
}
