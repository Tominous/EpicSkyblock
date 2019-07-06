package com.peaches.epicskyblock;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class IslandManager {

    public HashMap<String, Island> islands = new HashMap<>();

    public Direction direction = Direction.UNDEFINED;
    public String worldName = "EpicSkyblock";
    public Location nextLocation;

    public IslandManager() {
        makeWorld();
        nextLocation = new Location(getWorld(), 0, 0, 0);
    }

    private transient Schematic schematic;

    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }

    public Island createIsland(Player player) {
        Island island = new Island(player, getNextLocation().clone().subtract(EpicSkyblock.getConfiguration().size / 2, 0, EpicSkyblock.getConfiguration().size / 2), getNextLocation().clone().add(EpicSkyblock.getConfiguration().size / 2, getWorld().getMaxHeight(), EpicSkyblock.getConfiguration().size / 2), getNextLocation().clone().add(0, 100, 0), getNextLocation().clone().add(0.5, 97, -1.5));
        islands.put(player.getName(), island);

        island.generateIsland();
        island.teleportHome(player);

        NMSUtils.sendTitle(player, "&e&lIsland Created", 20, 40, 20);

        //Preparing for next Island
        direction = direction.next();
        switch (direction) {
            case NORTH:
                getNextLocation().add(EpicSkyblock.getConfiguration().distance, 0, 0);
            case EAST:
                getNextLocation().add(0, 0, EpicSkyblock.getConfiguration().distance);
            case SOUTH:
                getNextLocation().subtract(EpicSkyblock.getConfiguration().distance, 0, 0);
            case WEST:
                getNextLocation().subtract(0, 0, EpicSkyblock.getConfiguration().distance);
        }
        EpicSkyblock.getInstance().saveConfigs();

        return island;
    }

    private void makeWorld() {
        if (Bukkit.getWorld(worldName) == null) {
            WorldCreator wc = new WorldCreator(worldName);
            wc.generateStructures(false);
            wc.generator(new SkyblockGenerator());
            Bukkit.getServer().createWorld(wc);
        }
        new WorldCreator(worldName).generator(new SkyblockGenerator());
    }

    public Schematic getSchematic() throws IOException {
        File schematicFolder = new File(EpicSkyblock.getInstance().getDataFolder(), "schematics");
        if (!schematicFolder.exists()) {
            schematicFolder.mkdir();
        }
        File schematicFile = new File(schematicFolder, "island.schematic");

        if (!schematicFile.exists()) {
            if (EpicSkyblock.getInstance().getResource("schematics/island.schematic") != null) {
                EpicSkyblock.getInstance().saveResource("schematics/island.schematic", false);
            }
        }
        if (schematic == null) schematic = Schematic.loadSchematic(schematicFile);
        return schematic;
    }

    public Location getNextLocation() {
        return nextLocation;
    }
}
