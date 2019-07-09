package com.peaches.epicskyblock;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class IslandManager {

    public HashMap<Integer, Island> islands = new HashMap<>();
    public HashMap<String, User> users = new HashMap<>();

    public Direction direction = Direction.NORTH;
    public String worldName = "EpicSkyblock";
    public Location nextLocation;

    public int nextID = 1;

    public IslandManager() {
        makeWorld();
        nextLocation = new Location(getWorld(), 0, 0, 0);
    }

    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }

    public Island createIsland(Player player) {
        Location pos1 = nextLocation.clone().subtract(EpicSkyblock.getConfiguration().size.get(1).getSize() / 2, 0, EpicSkyblock.getConfiguration().size.get(1).getSize() / 2);
        Location pos2 = nextLocation.clone().add(EpicSkyblock.getConfiguration().size.get(1).getSize() / 2, 0, EpicSkyblock.getConfiguration().size.get(1).getSize() / 2);
        Location center = nextLocation.clone().add(0, 100, 0);
        Location home = nextLocation.clone().add(0.5, 97, -1.5);
        Island island = new Island(player, pos1, pos2, center, home, nextID);
        islands.put(nextID, island);

        User.getUser(player.getName()).islandID = nextID;

        island.generateIsland();
        island.teleportHome(player);

        NMSUtils.sendTitle(player, "&b&lIsland Created", 20, 40, 20);

        //TODO fix this cuz it doesnt work for some reason

        //Preparing for next Island
        direction = direction.next();
        switch (direction) {
            case NORTH:
                nextLocation.add(EpicSkyblock.getConfiguration().distance, 0, 0);
            case EAST:
                nextLocation.add(0, 0, EpicSkyblock.getConfiguration().distance);
            case SOUTH:
                nextLocation.subtract(EpicSkyblock.getConfiguration().distance, 0, 0);
            case WEST:
                nextLocation.subtract(0, 0, EpicSkyblock.getConfiguration().distance);
        }
        EpicSkyblock.getInstance().saveConfigs();

        nextID++;

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

    public void pasteSchematic(Location loc) {
        File schematicFolder = new File(EpicSkyblock.getInstance().getDataFolder(), "schematics");
        File schematicFile = new File(schematicFolder, "island.schematic");
        if (Bukkit.getPluginManager().getPlugin("WorldEdit").getDescription().getVersion().contains("7.")) {
            try {
                Clipboard clipboard = ClipboardFormats.findByFile(schematicFile).getReader(new FileInputStream(schematicFile)).read();
                EditSession editSession = com.sk89q.worldedit.WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(loc.getWorld()), -1);
                Operation operation = (new ClipboardHolder(clipboard)).createPaste(editSession).to(BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())).ignoreAirBlocks(true).build();
                Operations.complete(operation);
                editSession.close();
            } catch (Exception e) {
                EpicSkyblock.getInstance().sendErrorMessage(e);
            }
        } else {
            try {
                Schematic.loadSchematic(schematicFile).pasteSchematic(loc);
            } catch (Exception e) {
                EpicSkyblock.getInstance().sendErrorMessage(e);
            }
        }
    }

    public Island getIslandViaLocation(Location loc) {
        if (loc.getWorld().equals(getWorld())) {
            for (Island island : islands.values()) {
                if (island.isInIsland(loc)) {
                    return island;
                }
            }
        }
        return null;
    }
}
