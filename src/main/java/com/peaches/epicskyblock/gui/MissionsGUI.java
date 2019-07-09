package com.peaches.epicskyblock.gui;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.Island;
import com.peaches.epicskyblock.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class MissionsGUI {

    public Inventory inventory;
    public int islandID;
    public int scheduler;

    public ItemStack treasureHunter;
    public ItemStack competitor;
    public ItemStack miner;
    public ItemStack farmer;
    public ItemStack hunter;
    public ItemStack fisherman;
    public ItemStack builder;

    public MissionsGUI(Island island) {
        this.inventory = Bukkit.createInventory(null, 27, Utils.color(EpicSkyblock.getConfiguration().MissionsGUITitle));
        islandID = island.getId();
        scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(EpicSkyblock.getInstance(), this::addContent, 0, 10);
    }

    public void addContent() {
        Island island = EpicSkyblock.getIslandManager().islands.get(islandID);
        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 7, " "));
        }
        this.treasureHunter = Utils.makeItemHidden(Material.EXP_BOTTLE, 1, 0, "&b&lTreasure Hunter", Utils.color(new ArrayList<>(Arrays.asList("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bCollect " + EpicSkyblock.getMissions().treasureHunter.getAmount() + " Experience", "&b&l * &7Current Status: &b" + (island.treasureHunter != -1 ? island.treasureHunter + "/" + EpicSkyblock.getMissions().treasureHunter.getAmount() : "Completed"), "&b&l * &7Reward: &b" + EpicSkyblock.getMissions().treasureHunter.getReward() + " Island Crystals", "", "&b&l[!] &bComplete this mission for rewards."))));
        this.competitor = Utils.makeItemHidden(Material.GOLD_INGOT, 1, 0, "&b&lCompetitor", Utils.color(new ArrayList<>(Arrays.asList("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bGain " + EpicSkyblock.getMissions().competitor.getAmount() + " Island Value", "&b&l * &7Current Status: &b" + (island.competitor != Integer.MIN_VALUE ? island.competitor + "/" + EpicSkyblock.getMissions().competitor.getAmount() : "Completed"), "&b&l * &7Reward: &b" + EpicSkyblock.getMissions().competitor.getReward() + " Island Crystals", "", "&b&l[!] &bComplete this mission for rewards."))));
        this.miner = Utils.makeItemHidden(Material.DIAMOND_ORE, 1, 0, "&b&lMiner", Utils.color(new ArrayList<>(Arrays.asList("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bDestroy " + EpicSkyblock.getMissions().miner.getAmount() + " Ores", "&b&l * &7Current Status: &b" + (island.miner != -1 ? island.miner + "/" + EpicSkyblock.getMissions().miner.getAmount() : "Completed"), "&b&l * &7Reward: &b" + EpicSkyblock.getMissions().miner.getReward() + " Island Crystals", "", "&b&l[!] &bComplete this mission for rewards."))));
        this.farmer = Utils.makeItemHidden(Material.SUGAR_CANE, 1, 0, "&b&lFarmer", Utils.color(new ArrayList<>(Arrays.asList("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bHarvest " + EpicSkyblock.getMissions().farmer.getAmount() + " Crops", "&b&l * &7Current Status: &b" + (island.farmer != -1 ? island.farmer + "/" + EpicSkyblock.getMissions().farmer.getAmount() : "Completed"), "&b&l * &7Reward: &b" + EpicSkyblock.getMissions().farmer.getReward() + " Island Crystals", "", "&b&l[!] &bComplete this mission for rewards."))));
        this.hunter = Utils.makeItemHidden(Material.BLAZE_POWDER, 1, 0, "&b&lHunter", Utils.color(new ArrayList<>(Arrays.asList("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bKill " + EpicSkyblock.getMissions().hunter.getAmount() + " Mobs", "&b&l * &7Current Status: &b" + (island.hunter != -1 ? island.hunter + "/" + EpicSkyblock.getMissions().hunter.getAmount() : "Completed"), "&b&l * &7Reward: &b" + EpicSkyblock.getMissions().hunter.getReward() + " Island Crystals", "", "&b&l[!] &bComplete this mission for rewards."))));
        this.fisherman = Utils.makeItemHidden(Material.FISHING_ROD, 1, 0, "&b&lFisherman", Utils.color(new ArrayList<>(Arrays.asList("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bCatch " + EpicSkyblock.getMissions().fisherman.getAmount() + " Fish", "&b&l * &7Current Status: &b" + (island.fisherman != -1 ? island.fisherman + "/" + EpicSkyblock.getMissions().fisherman.getAmount() : "Completed"), "&b&l * &7Reward: &b" + EpicSkyblock.getMissions().fisherman.getReward() + " Island Crystals", "", "&b&l[!] &bComplete this mission for rewards."))));
        this.builder = Utils.makeItemHidden(Material.COBBLESTONE, 1, 0, "&b&lBuilder", Utils.color(new ArrayList<>(Arrays.asList("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bPlace " + EpicSkyblock.getMissions().builder.getAmount() + " Blocks", "&b&l * &7Current Status: &b" + (island.builder != -1 ? island.builder + "/" + EpicSkyblock.getMissions().builder.getAmount() : "Completed"), "&b&l * &7Reward: &b" + EpicSkyblock.getMissions().builder.getReward() + " Island Crystals", "", "&b&l[!] &bComplete this mission for rewards."))));

        inventory.setItem(10, this.treasureHunter);
        inventory.setItem(11, this.competitor);
        inventory.setItem(12, this.miner);
        inventory.setItem(13, this.farmer);
        inventory.setItem(14, this.hunter);
        inventory.setItem(15, this.fisherman);
        inventory.setItem(16, this.builder);
    }
}
