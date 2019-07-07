package com.peaches.epicskyblock;

import com.peaches.epicskyblock.gui.BoosterGUI;
import com.peaches.epicskyblock.gui.MembersGUI;
import com.peaches.epicskyblock.gui.MissionsGUI;
import com.peaches.epicskyblock.gui.UpgradeGUI;
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

    private transient UpgradeGUI upgradeGUI;
    private transient BoosterGUI boosterGUI;
    private transient MissionsGUI missionsGUI;
    private transient MembersGUI membersGUI;

    private int id;

    private int spawnerBooster;
    private int farmingBooster;
    private int expBooster;
    private int flightBooster;

    private int boosterid;

    private int crystals;

    private int sizeLevel;
    private int memberLevel;
    private int warpLevel;

    public Island(Player owner, Location pos1, Location pos2, Location center, Location home, int id) {
        this.owner = owner.getName();
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.center = center;
        this.home = home;
        this.members = new ArrayList<>(Collections.singletonList(owner.getName()));
        this.id = id;
        upgradeGUI = new UpgradeGUI(this);
        boosterGUI = new BoosterGUI(this);
        missionsGUI = new MissionsGUI(this);
        membersGUI = new MembersGUI(this);
        spawnerBooster = 0;
        farmingBooster = 0;
        expBooster = 0;
        flightBooster = 0;
        crystals = 0;
        sizeLevel = 1;
        memberLevel = 1;
        warpLevel = 1;
        init();
    }

    public void addUser(User user) {
        if (members.size() < EpicSkyblock.getConfiguration().member.get(memberLevel).getSize()) {
            user.islandID = id;
            user.invites.clear();
            members.add(user.player);
            teleportHome(Bukkit.getPlayer(user.player));
            user.invites.clear();
        } else {
            Bukkit.getPlayer(user.player).sendMessage("Max Member Count reached");
        }
    }

    public boolean isInIsland(Location location) {
        return (location.getX() > getPos1().getX() && location.getX() <= getPos2().getX()) && (location.getZ() > getPos1().getZ() && location.getZ() <= getPos2().getZ());
    }

    public void init() {
        initChunks();
        boosterid = Bukkit.getScheduler().scheduleAsyncRepeatingTask(EpicSkyblock.getInstance(), () -> {
            if (spawnerBooster > 0) spawnerBooster--;
            if (farmingBooster > 0) farmingBooster--;
            if (expBooster > 0) expBooster--;
            if (flightBooster > 0) flightBooster--;
            if (flightBooster == 0) {
                for (String player : members) {
                    Player p = Bukkit.getPlayer(player);
                    if (p != null) {
                        if (!p.hasPermission("EpicSkyblock.Fly")) {
                            p.setAllowFlight(false);
                            p.setFlying(false);
                        }
                    }
                }
            }
        }, 0, 20);
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
        NMSUtils.sendWorldBorder(p, NMSUtils.Color.Blue, EpicSkyblock.getConfiguration().size.get(sizeLevel).getSize(), getCenter());
    }

    public void delete() {
        Bukkit.getScheduler().cancelTask(getMembersGUI().scheduler);
        Bukkit.getScheduler().cancelTask(getBoosterGUI().scheduler);
        Bukkit.getScheduler().cancelTask(getMissionsGUI().scheduler);
        Bukkit.getScheduler().cancelTask(getUpgradeGUI().scheduler);
        deleteBlocks();
        killEntities();
        for (String player : members) {
            User.getUser(player).islandID = 0;
            if (Bukkit.getPlayer(player) != null) Bukkit.getPlayer(player).closeInventory();
        }
        for (Chunk c : chunks) {
            for (Entity e : c.getEntities()) {
                if (e instanceof Player) {
                    Player p = (Player) e;
                    NMSUtils.sendWorldBorder(p, NMSUtils.Color.Blue, Integer.MAX_VALUE, getCenter());
                }
            }
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
        Bukkit.getScheduler().cancelTask(boosterid);
        boosterid = -1;
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

    public UpgradeGUI getUpgradeGUI() {
        if (upgradeGUI == null) upgradeGUI = new UpgradeGUI(this);
        return upgradeGUI;
    }

    public BoosterGUI getBoosterGUI() {
        if (boosterGUI == null) boosterGUI = new BoosterGUI(this);
        return boosterGUI;
    }

    public MissionsGUI getMissionsGUI() {
        if (missionsGUI == null) missionsGUI = new MissionsGUI(this);
        return missionsGUI;
    }

    public MembersGUI getMembersGUI() {
        if (membersGUI == null) membersGUI = new MembersGUI(this);
        return membersGUI;
    }

    public int getSpawnerBooster() {
        return spawnerBooster;
    }

    public void setSpawnerBooster(int spawnerBooster) {
        this.spawnerBooster = spawnerBooster;
    }

    public int getFarmingBooster() {
        return farmingBooster;
    }

    public void setFarmingBooster(int farmingBooster) {
        this.farmingBooster = farmingBooster;
    }

    public int getExpBooster() {
        return expBooster;
    }

    public void setExpBooster(int expBooster) {
        this.expBooster = expBooster;
    }

    public int getFlightBooster() {
        return flightBooster;
    }

    public void setFlightBooster(int flightBooster) {
        this.flightBooster = flightBooster;
    }

    public int getCrystals() {
        return crystals;
    }

    public void setCrystals(int crystals) {
        this.crystals = crystals;
    }

    public List<String> getMembers() {
        return members;
    }

    public int getSizeLevel() {
        return sizeLevel;
    }

    public void setSizeLevel(int sizeLevel) {
        this.sizeLevel = sizeLevel;

        pos1 = getCenter().clone().subtract(EpicSkyblock.getConfiguration().size.get(sizeLevel).getSize() / 2, 0, EpicSkyblock.getConfiguration().size.get(sizeLevel).getSize() / 2);
        pos2 = getCenter().clone().add(EpicSkyblock.getConfiguration().size.get(sizeLevel).getSize() / 2, 0, EpicSkyblock.getConfiguration().size.get(sizeLevel).getSize() / 2);
    }

    public int getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(int memberLevel) {
        this.memberLevel = memberLevel;
    }

    public int getWarpLevel() {
        return warpLevel;
    }

    public void setWarpLevel(int warpLevel) {
        this.warpLevel = warpLevel;
    }
}
