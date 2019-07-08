package com.peaches.epicskyblock.gui;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.Island;
import com.peaches.epicskyblock.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class BorderColorGUI {

    public Inventory inventory;
    public int islandID;
    public int scheduler;

    public ItemStack red;
    public ItemStack green;
    public ItemStack blue;

    public BorderColorGUI(Island island) {
        this.inventory = Bukkit.createInventory(null, 27, Utils.color(EpicSkyblock.getConfiguration().BorderColorGUITitle));
        islandID = island.getId();
        scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(EpicSkyblock.getInstance(), this::addContent, 0, 10);
    }

    public void addContent() {
        Island island = EpicSkyblock.getIslandManager().islands.get(islandID);
        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 7, " "));
        }
        int i = 0;
        this.red = Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 14, "&c&lRed");
        this.green = Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 5, "&a&lGreen");
        this.blue = Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 11, "&b&lBlue");

        inventory.setItem(11, this.red);
        inventory.setItem(13, this.blue);
        inventory.setItem(15, this.green);
    }
}
