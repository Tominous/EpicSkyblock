package com.peaches.epicskyblock;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Island {
    private transient List<Chunk> chunks;

    private String owner;
    private List<String> members;
    private Location pos1;
    private Location pos2;
    private Location center;
    private Location home;

    private int id;

    public Island(Player owner, Location pos1, Location pos2, Location center, Location home, int id) {
        this.owner = owner.getName();
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.center = center;
        this.home = home;
        this.members = new ArrayList<>(Collections.singletonList(owner.getName()));
        this.id = id;
        initChunks();
    }

    public void addUser(User user) {
        user.islandID = id;
        user.invites.clear();
        members.add(user.player);
    }

    public void initChunks() {
        chunks = new ArrayList<>();
        for (int X = getPos1().getChunk().getX(); X <= getPos2().getChunk().getX(); X++) {
            for (int Z = getPos1().getChunk().getZ(); Z <= getPos2().getChunk().getZ(); Z++) {
                chunks.add(EpicSkyblock.getIslandManager().getWorld().getChunkAt(X, Z));
            }
        }
    }

    public void generateIsland() {
        deleteBlocks();
        killEntities();
        try {
            EpicSkyblock.getIslandManager().getSchematic().pasteSchematic(getCenter().clone());
        } catch (IOException e) {
            EpicSkyblock.getInstance().sendErrorMessage(e);
        }
    }

    public void teleportHome(Player p) {
        p.teleport(getHome());
        NMSUtils.sendWorldBorder(p, NMSUtils.Color.Blue, EpicSkyblock.getConfiguration().size / 2, getCenter());
    }

    public void delete() {
        deleteBlocks();
        killEntities();
        for (String player : members) {
            User.getUser(player).islandID = 0;
        }
        this.owner = null;
        this.pos1 = null;
        this.pos2 = null;
        this.members = null;
        this.chunks = null;
        this.center = null;
        this.home = null;
        EpicSkyblock.getIslandManager().islands.remove(this.id);
        this.id = 0;
        EpicSkyblock.getInstance().saveConfigs();
    }

    public void deleteBlocks() {
        for (double X = getPos1().getX(); X <= getPos2().getX(); X++) {
            for (double Y = 0; Y <= EpicSkyblock.getIslandManager().getWorld().getMaxHeight(); Y++) {
                for (double Z = getPos1().getZ(); Z <= getPos2().getZ(); Z++) {
                    new Location(getPos1().getWorld(), X, Y, Z).getBlock().setType(Material.AIR, false);
                }
            }
        }
    }

    public void killEntities() {
        for (Chunk c : chunks) {
            for (Entity e : c.getEntities()) {
                if (e.getType() != EntityType.PLAYER) {
                    e.remove();
                } else {
                    Player p = (Player) e;
                    NMSUtils.sendWorldBorder(p, NMSUtils.Color.Blue, Integer.MAX_VALUE, getCenter());
                }
            }
        }
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public Location getCenter() {
        return center;
    }

    public Location getHome() {
        return home;
    }

    public Player getOwner() {
        return Bukkit.getPlayer(owner);
    }

    public int getId() {
        return id;
    }
}
