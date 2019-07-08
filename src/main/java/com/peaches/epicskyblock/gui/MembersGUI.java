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

public class MembersGUI {

    public Inventory inventory;
    public int islandID;
    public int scheduler;

    public MembersGUI(Island island) {
        this.inventory = Bukkit.createInventory(null, 27, Utils.color(EpicSkyblock.getConfiguration().MembersGUITitle));
        islandID = island.getId();
        scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(EpicSkyblock.getInstance(), this::addContent, 0, 10);
    }

    public void addContent() {
        Island island = EpicSkyblock.getIslandManager().islands.get(islandID);
        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 7, " "));
        }
        int i = 0;
        for (String member : island.getMembers()) {
            ItemStack head = Utils.makeItem(Material.SKULL_ITEM, 1, 3, "&b&l" + member);
            SkullMeta m = (SkullMeta) head.getItemMeta();
            m.setOwner(member);
            head.setItemMeta(m);
            inventory.setItem(i, head);
            i++;
        }
    }
}
