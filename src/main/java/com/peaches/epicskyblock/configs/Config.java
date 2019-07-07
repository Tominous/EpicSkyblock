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
    public HashMap<Integer, Upgrade> size = new HashMap<Integer, Upgrade>() {{
        put(1, new Upgrade(50, 15));
        put(2, new Upgrade(100, 15));
        put(3, new Upgrade(150, 15));
    }};
    public HashMap<Integer, Upgrade> member = new HashMap<Integer, Upgrade>() {{
        put(1, new Upgrade(9, 15));
        put(2, new Upgrade(18, 15));
        put(3, new Upgrade(27, 15));
    }};
    public Biome defaultBiome = Biome.PLAINS;
    public int spawnerBoosterCost = 15;
    public int farmingBoosterCost = 15;
    public int experienceBoosterCost = 15;
    public int flightBoosterCost = 15;

    public class Upgrade {
        private int size;
        private int cost;

        public Upgrade(int size, int cost) {
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
