package com.peaches.epicskyblock.gui;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.Island;
import com.peaches.epicskyblock.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpgradeGUI {

    public Inventory inventory;
    public ItemStack size;
    public ItemStack member;
    public ItemStack warp;
    public int islandID;
    public int scheduler;

    public UpgradeGUI(Island island) {
        this.inventory = Bukkit.createInventory(null, 27, Utils.color(EpicSkyblock.getConfiguration().UpgradeGUITitle));
        islandID = island.getId();
        scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(EpicSkyblock.getInstance(), this::addContent, 0, 10);
    }

    public void addContent() {
        int currentsize = EpicSkyblock.getIslandManager().islands.get(islandID).getSizeLevel();
        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 7, " "));
        }
        List<String> sizeLore = new ArrayList<>(Arrays.asList("&7Need more room to expand? Buy this", "&7upgrade to increase your island size.", "", "&b&lInformation:", "&b&l * &7Current Level: &b" +currentsize, "&b&l * &7Current Size: &b"+EpicSkyblock.getConfiguration().size.get(currentsize)+"x"+EpicSkyblock.getConfiguration().size.get(currentsize)+" Blocks", "&b&l * &7Upgrade Cost: &b", "", "&b&lLevels:"));
        for (int level : EpicSkyblock.getConfiguration().size.keySet()) {
            sizeLore.add("&b&l * &7Level " + level + ": &b" + EpicSkyblock.getConfiguration().size.get(level) + "x" + EpicSkyblock.getConfiguration().size.get(level) + " Blocks");
        }
        sizeLore.add("");
        sizeLore.add("&b&l[!] &bRight Click to Purchase this Upgrade");
        this.size = Utils.makeItem(Material.GRASS, 1, 0, "&b&lIsland Size", Utils.color(sizeLore));
        this.member = Utils.makeItem(Material.ARMOR_STAND, 1, 0, "&b&lIsland Member Count", Utils.color(new ArrayList<>(Arrays.asList("&7Need more members? Buy this", "&7upgrade to increase your member count.", "", "&b&lInformation:", "&b&l * &7Current Level: &b", "&b&l * &7Current Size: &b", "&b&l * &7Upgrade Cost: &b", "", "&b&lLevels:", "", "&b&l[!] &bRight Click to Purchase this Upgrade"))));
        this.warp = Utils.makeItem(Material.ENDER_PORTAL_FRAME, 1, 0, "&b&lIsland Warp", Utils.color(new ArrayList<>(Arrays.asList("&7Need more island warps? Buy this", "&7upgrade to increase your warp count.", "", "&b&lInformation:", "&b&l * &7Current Level: &b", "&b&l * &7Current Size: &b", "&b&l * &7Upgrade Cost: &b", "", "&b&lLevels:", "", "&b&l[!] &bRight Click to Purchase this Upgrade"))));
        inventory.setItem(10, size);
        inventory.setItem(13, member);
        inventory.setItem(16, warp);
    }
}
