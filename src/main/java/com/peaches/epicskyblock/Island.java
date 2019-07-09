package com.peaches.epicskyblock;

import com.peaches.epicskyblock.gui.*;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Island {

    public class Warp {
        Location location;
        String name;
        String password;

        public Warp(Location location, String name, String password) {
            this.location = location;
            this.name = name;
            this.password = password;
        }

        public Location getLocation() {
            return location;
        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }
    }

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
    private transient WarpGUI warpGUI;
    private transient BorderColorGUI borderColorGUI;

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

    private Integer a;

    private int value;

    public transient List<Location> blocks;

    private List<Warp> warps;

    private int startvalue;

    public int treasureHunter;
    public int competitor;
    public int miner;
    public int farmer;
    public int hunter;
    public int fisherman;
    public int builder;

    private boolean visit;

    private NMSUtils.Color borderColor;

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
        warpGUI = new WarpGUI(this);
        borderColorGUI = new BorderColorGUI(this);
        spawnerBooster = 0;
        farmingBooster = 0;
        expBooster = 0;
        flightBooster = 0;
        crystals = 0;
        sizeLevel = 1;
        memberLevel = 1;
        warpLevel = 1;
        value = 0;
        warps = new ArrayList<>();
        treasureHunter = 0;
        competitor = 0;
        miner = 0;
        farmer = 0;
        hunter = 0;
        fisherman = 0;
        builder = 0;
        startvalue = -1;
        borderColor = NMSUtils.Color.Blue;
        visit = true;
        init();
    }

    public void sendBorder() {
        for (Chunk c : chunks) {
            for (Entity e : c.getEntities()) {
                if (e instanceof Player) {
                    Player p = (Player) e;
                    sendBorder(p);
                }
            }
        }
    }

    public void hideBorder() {
        for (Chunk c : chunks) {
            for (Entity e : c.getEntities()) {
                if (e instanceof Player) {
                    Player p = (Player) e;
                    hideBorder(p);
                }
            }
        }
    }

    public void sendBorder(Player p) {
        NMSUtils.sendWorldBorder(p, borderColor, EpicSkyblock.getConfiguration().size.get(sizeLevel).getSize(), getCenter());
    }

    public void hideBorder(Player p) {
        NMSUtils.sendWorldBorder(p, borderColor, Integer.MAX_VALUE, getCenter());
    }

    public void completeMission(String Mission, int Reward) {
        crystals += Reward;
        for (String member : members) {
            Player p = Bukkit.getPlayer(member);
            if (p != null) {
                NMSUtils.sendTitle(p, "&b&lMission Complete: &7" + Mission, 20, 40, 20);
                NMSUtils.sendSubTitle(p, "&bReward: &7" + Reward, 20, 40, 20);
            }
        }
    }

    public void calculateIslandValue() {
        int v = 0;
        for (Location loc : blocks) {
            Block b = loc.getBlock();
            if (EpicSkyblock.getConfiguration().blockvalue.containsKey(b.getType())) {
                v += EpicSkyblock.getConfiguration().blockvalue.get(b.getType());
            }
            if (loc.getBlock().getState() instanceof CreatureSpawner) {
                CreatureSpawner spawner = (CreatureSpawner) b.getState();
                if (EpicSkyblock.getConfiguration().spawnervalue.containsKey(spawner.getSpawnedType().name())) {
                    v += EpicSkyblock.getConfiguration().spawnervalue.get(spawner.getSpawnedType().name());
                }
            }
        }
        this.value = v;
        if (startvalue == -1) startvalue = v;

        if (competitor != Integer.MIN_VALUE) {
            this.competitor = v - startvalue;
            if (competitor >= EpicSkyblock.getMissions().competitor.getAmount()) {
                competitor = Integer.MIN_VALUE;
                completeMission("Competitor", EpicSkyblock.getMissions().competitor.getReward());
            }
        }
    }

    public void initBlocks() {
        blocks.clear();
        this.a = Bukkit.getScheduler().scheduleSyncRepeatingTask(EpicSkyblock.getInstance(), new Runnable() {

            double Y = 0;

            @Override
            public void run() {
                if (Y <= EpicSkyblock.getIslandManager().getWorld().getMaxHeight()) {
                    for (double X = getPos1().getX(); X <= getPos2().getX(); X++) {
                        for (double Z = getPos1().getZ(); Z <= getPos2().getZ(); Z++) {
                            Location loc = new Location(getPos1().getWorld(), X, Y, Z);
                            if (Utils.isBlockValuable(loc.getBlock())) {
                                if (!blocks.contains(loc)) blocks.add(loc);
                            }
                        }
                    }
                } else {
                    Bukkit.getScheduler().cancelTask(a);
                    a = -1;
                }
                Y++;
            }
        }, 0, 1);
    }

    public void addWarp(Player player, Location location, String name, String password) {
        if (warps.size() < EpicSkyblock.getConfiguration().warp.get(warpLevel).getSize()) {
            warps.add(new Warp(location, name, password));
            player.sendMessage(Utils.color(EpicSkyblock.getMessages().warpAdded.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
        } else {
            player.sendMessage(Utils.color(EpicSkyblock.getMessages().maxWarpsReached.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
        }
    }

    public void addUser(User user) {
        if (members.size() < EpicSkyblock.getConfiguration().member.get(memberLevel).getSize()) {
            user.islandID = id;
            user.invites.clear();
            members.add(user.player);
            teleportHome(Bukkit.getPlayer(user.player));
            user.invites.clear();
        } else {
            Bukkit.getPlayer(user.player).sendMessage(Utils.color(EpicSkyblock.getMessages().maxMemberCount.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
        }
    }

    public void removeUser(User user) {
        user.islandID = 0;
        members.remove(user.player);
    }

    public boolean isInIsland(Location location) {
        return (location.getX() > getPos1().getX() && location.getX() <= getPos2().getX()) && (location.getZ() > getPos1().getZ() && location.getZ() <= getPos2().getZ());
    }

    public void init() {
        blocks = new ArrayList<>();
        initChunks();
        initBlocks();
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
        Bukkit.getScheduler().scheduleSyncRepeatingTask(EpicSkyblock.getInstance(), this::calculateIslandValue, 0, 20);
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
        EpicSkyblock.getIslandManager().pasteSchematic(getCenter().clone());
    }

    public void teleportHome(Player p) {
        p.sendMessage(Utils.color(EpicSkyblock.getMessages().teleportingHome.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
        if (Utils.isSafe(getHome())) {
            p.teleport(getHome());
            sendBorder(p);
        } else {

            Location loc = Utils.getNewHome(this, this.home);
            if (loc != null) {
                this.home = loc;
                p.teleport(this.home);
                sendBorder(p);
            } else {
                generateIsland();
                teleportHome(p);
                sendBorder(p);
            }
        }
    }

    public void delete() {
        Bukkit.getScheduler().cancelTask(getMembersGUI().scheduler);
        Bukkit.getScheduler().cancelTask(getBoosterGUI().scheduler);
        Bukkit.getScheduler().cancelTask(getMissionsGUI().scheduler);
        Bukkit.getScheduler().cancelTask(getUpgradeGUI().scheduler);
        Bukkit.getScheduler().cancelTask(getWarpGUI().scheduler);
        if (a != -1) Bukkit.getScheduler().cancelTask(a);
        deleteBlocks();
        killEntities();
        for (String player : members) {
            User.getUser(player).islandID = 0;
            if (Bukkit.getPlayer(player) != null) Bukkit.getPlayer(player).closeInventory();
        }
        hideBorder();
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

    public String getOwner() {
        return owner;
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

    public WarpGUI getWarpGUI() {
        if (warpGUI == null) warpGUI = new WarpGUI(this);
        return warpGUI;
    }

    public BorderColorGUI getBorderColorGUI() {
        if (borderColorGUI == null) borderColorGUI = new BorderColorGUI(this);
        return borderColorGUI;
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

    public List<Warp> getWarps() {
        return warps;
    }

    public int getValue() {
        return value;
    }

    public NMSUtils.Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(NMSUtils.Color borderColor) {
        this.borderColor = borderColor;
    }

    public boolean isVisit() {
        return visit;
    }

    public void setVisit(boolean visit) {
        this.visit = visit;
    }
}
