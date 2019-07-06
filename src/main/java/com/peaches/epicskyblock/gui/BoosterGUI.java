package com.peaches.epicskyblock.gui;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.Island;
import com.peaches.epicskyblock.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BoosterGUI {

    public Inventory inventory;
    public int islandID;

    public BoosterGUI(Island island) {
        this.inventory = Bukkit.createInventory(null, 27, EpicSkyblock.getConfiguration().BoosterGUITitle);
        islandID = island.getId();
        addContent();
    }

    public void addContent() {
        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 7, " "));
        }
    }
}
