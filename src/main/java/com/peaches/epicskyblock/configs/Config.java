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
    public HashMap<Integer, SizeUpgrade> size = new HashMap<Integer, SizeUpgrade>() {{
        put(1, new SizeUpgrade(50, 15));
        put(2, new SizeUpgrade(100, 15));
        put(3, new SizeUpgrade(150, 15));
    }};
    public Biome defaultBiome = Biome.PLAINS;
    public int spawnerBoosterCost = 15;
    public int farmingBoosterCost = 15;
    public int experienceBoosterCost = 15;
    public int flightBoosterCost = 15;

    public class SizeUpgrade {
        private int size;
        private int cost;

        public SizeUpgrade(int size, int cost) {
            this.size = size;
            this.cost = cost;
        }

        public int getSize() {
            return size;
        }

        public int getCost() {
            return cost;
        }
    }
}
