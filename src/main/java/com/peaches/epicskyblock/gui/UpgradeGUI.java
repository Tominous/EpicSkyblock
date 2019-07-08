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
        Island island = EpicSkyblock.getIslandManager().islands.get(islandID);
        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 7, " "));
        }

        int currentsize = island.getSizeLevel();
        String sizecost = EpicSkyblock.getConfiguration().size.containsKey(currentsize + 1) ? EpicSkyblock.getConfiguration().size.get(currentsize + 1).getCost() + " Crystals" : "Max Level Reached";
        List<String> sizeLore = new ArrayList<>(Arrays.asList("&7Need more room to expand? Buy this", "&7upgrade to increase your island size.", "", "&b&lInformation:", "&b&l * &7Current Level: &b" + currentsize, "&b&l * &7Current Size: &b" + EpicSkyblock.getConfiguration().size.get(currentsize).getSize() + "x" + EpicSkyblock.getConfiguration().size.get(currentsize).getSize() + " Blocks", "&b&l * &7Upgrade Cost: &b" + sizecost, "", "&b&lLevels:"));
        for (int level : EpicSkyblock.getConfiguration().size.keySet()) {
            sizeLore.add("&b&l * &7Level " + level + ": &b" + EpicSkyblock.getConfiguration().size.get(level).getSize() + "x" + EpicSkyblock.getConfiguration().size.get(level).getSize() + " Blocks");
        }
        sizeLore.add("");
        sizeLore.add("&b&l[!] &bRight Click to Purchase this Upgrade");
        this.size = Utils.makeItem(Material.GRASS, 1, 0, "&b&lIsland Size", Utils.color(sizeLore));


        int currentmember = island.getMemberLevel();
        String membercost = EpicSkyblock.getConfiguration().member.containsKey(currentmember + 1) ? EpicSkyblock.getConfiguration().member.get(currentmember + 1).getCost() + " Crystals" : "Max Level Reached";
        List<String> memberLore = new ArrayList<>(Arrays.asList("&7Need more members? Buy this", "&7upgrade to increase your member count.", "", "&b&lInformation:", "&b&l * &7Current Level: &b" + currentmember, "&b&l * &7Current Members: &b" + EpicSkyblock.getConfiguration().member.get(currentmember).getSize() + " Members", "&b&l * &7Upgrade Cost: &b" + membercost, "", "&b&lLevels:"));
        for (int level : EpicSkyblock.getConfiguration().member.keySet()) {
            memberLore.add("&b&l * &7Level " + level + ": &b" + EpicSkyblock.getConfiguration().member.get(level).getSize() + " Members");
        }
        memberLore.add("");
        memberLore.add("&b&l[!] &bRight Click to Purchase this Upgrade");
        this.member = Utils.makeItem(Material.ARMOR_STAND, 1, 0, "&b&lIsland Member Count", Utils.color(memberLore));


        int currentwarp = island.getWarpLevel();
        String warpcost = EpicSkyblock.getConfiguration().warp.containsKey(currentwarp + 1) ? EpicSkyblock.getConfiguration().warp.get(currentwarp + 1).getCost() + " Crystals" : "Max Level Reached";
        List<String> warpLore = new ArrayList<>(Arrays.asList("&7Need more island warps? Buy this", "&7upgrade to increase your warp count.", "", "&b&lInformation:", "&b&l * &7Current Level: &b" + currentwarp, "&b&l * &7Current Warps: &b" + EpicSkyblock.getConfiguration().warp.get(currentwarp).getSize() + " Warps", "&b&l * &7Upgrade Cost: &b" + warpcost, "", "&b&lLevels:"));
        for (int level : EpicSkyblock.getConfiguration().warp.keySet()) {
            warpLore.add("&b&l * &7Level " + level + ": &b" + EpicSkyblock.getConfiguration().warp.get(level).getSize() + " Warps");
        }
        warpLore.add("");
        warpLore.add("&b&l[!] &bRight Click to Purchase this Upgrade");
        this.warp = Utils.makeItem(Material.ENDER_PORTAL_FRAME, 1, 0, "&b&lIsland Warp", Utils.color(warpLore));
        inventory.setItem(10, size);
        inventory.setItem(13, member);
        inventory.setItem(16, warp);
    }
}
