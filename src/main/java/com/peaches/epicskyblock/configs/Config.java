package com.peaches.epicskyblock.configs;

import org.bukkit.block.Biome;

import java.util.HashMap;

public class Config {

    public String prefix = "&b&lEpicSkyblock";
    public String UpgradeGUITitle = "&7Upgrade";
    public String BoosterGUITitle = "&7Booster";
    public String MissionsGUITitle = "&7Missions";
    public String MembersGUITitle = "&7Members";
    public boolean sendErrorReports = true;
    public int distance = 200;
    public HashMap<Integer, Integer> size = new HashMap<Integer, Integer>() {{
        put(1, 50);
        put(2, 100);
        put(3, 150);
    }};
    public Biome defaultBiome = Biome.PLAINS;
    public int spawnerBoosterCost = 15;
    public int farmingBoosterCost = 15;
    public int experienceBoosterCost = 15;
    public int flightBoosterCost = 15;

}
