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

public class BoosterGUI {

    public Inventory inventory;
    public int islandID;

    public ItemStack spawner;
    public ItemStack farming;
    public ItemStack exp;
    public ItemStack flight;

    public BoosterGUI(Island island) {
        this.inventory = Bukkit.createInventory(null, 27, Utils.color(EpicSkyblock.getConfiguration().BoosterGUITitle));
        islandID = island.getId();
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(EpicSkyblock.getInstance(), this::addContent, 0, 10);
    }

    public void addContent() {
        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 7, " "));
        }
        this.spawner = Utils.makeItem(Material.MOB_SPAWNER, 1, 0, "&b&lIncreased Mobs", Utils.color(new ArrayList<>(Arrays.asList("&7Are your spawners too slow? Buy this", "&7booster and increase spawner rates x2.", "", "&b&lInformation:", "&b&l * &7Time Remaining: &b" + EpicSkyblock.getIslandManager().islands.get(islandID).getSpawnerBooster() + "s", "&b&l * &7Booster Cost: &b" + EpicSkyblock.getConfiguration().spawnerBoosterCost + " Crystals", "", "&b&l[!] &bRight Click to Purchase this Booster."))));
        this.farming = Utils.makeItem(Material.WHEAT, 1, 0, "&b&lIncreased Crops", Utils.color(new ArrayList<>(Arrays.asList("&7Are your crops too slow? Buy this", "&7booster and increase crop growth rates x2.", "", "&b&lInformation:", "&b&l * &7Time Remaining: &b" + EpicSkyblock.getIslandManager().islands.get(islandID).getFarmingBooster() + "s", "&b&l * &7Booster Cost: &b" + EpicSkyblock.getConfiguration().farmingBoosterCost + " Crystals", "", "&b&l[!] &bRight Click to Purchase this Booster."))));
        this.exp = Utils.makeItem(Material.EXP_BOTTLE, 1, 0, "&b&lIncreased Experience", Utils.color(new ArrayList<>(Arrays.asList("&7Takes too long to get exp? Buy this", "&7booster and exp rates x2.", "", "&b&lInformation:", "&b&l * &7Time Remaining: &b" + EpicSkyblock.getIslandManager().islands.get(islandID).getExpBooster() + "s", "&b&l * &7Booster Cost: &b" + EpicSkyblock.getConfiguration().experienceBoosterCost + "Crystals", "", "&b&l[!] &bRight Click to Purchase this Booster."))));
        this.flight = Utils.makeItem(Material.FEATHER, 1, 0, "&b&lIncreased Flight", Utils.color(new ArrayList<>(Arrays.asList("&7Tired of falling off your island? Buy this", "&7booster and allow all members to fly.", "", "&b&lInformation:", "&b&l * &7Time Remaining: &b" + EpicSkyblock.getIslandManager().islands.get(islandID).getFlightBooster() + "s", "&b&l * &7Booster Cost: &b" + EpicSkyblock.getConfiguration().flightBoosterCost + " Crystals", "", "&b&l[!] &bRight Click to Purchase this Booster."))));
        inventory.setItem(10, spawner);
        inventory.setItem(12, farming);
        inventory.setItem(14, exp);
        inventory.setItem(16, flight);
    }
}
