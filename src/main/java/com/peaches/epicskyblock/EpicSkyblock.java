package com.peaches.epicskyblock;

import com.peaches.epicskyblock.commands.CommandManager;
import com.peaches.epicskyblock.configs.Config;
import com.peaches.epicskyblock.configs.Messages;
import com.peaches.epicskyblock.configs.Missions;
import com.peaches.epicskyblock.configs.OreGen;
import com.peaches.epicskyblock.listeners.*;
import com.peaches.epicskyblock.placeholders.ClipPlaceholderAPIManager;
import com.peaches.epicskyblock.serializer.Persist;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;

public class EpicSkyblock extends JavaPlugin {

    private static EpicSkyblock instance;

    private static Config configuration;
    private static Missions missions;
    private static OreGen oreGen;
    private static Messages messages;
    private static Persist persist;

    private static IslandManager islandManager;

    private static CommandManager commandManager;

    private ClipPlaceholderAPIManager clipPlaceholderAPIManager;
    /*
    TODO
    Way to edit warps
    Messages
    Permissions/Ranks: Owner Moderator Trusted Member
    Add 1.13 Schematics
    Border Color GUI
     */

    @Override
    public void onEnable() {
        super.onEnable();
        getDataFolder().mkdir();
        loadSchematic();

        instance = this;

        persist = new Persist();

        commandManager = new CommandManager("island");
        commandManager.registerCommands();

        loadConfigs();
        saveConfigs();

        registerListeners(new onBlockBreak(), new onBlockPlace(), new onClick(), new onBlockFromTo(), new onPlayerMove(), new onInventoryClick(), new onSpawnerSpawn(), new onEntityDeath(), new onPlayerJoinLeave(), new onBlockGrow(), new onPlayerTalk(), new onEntityDamage(), new onEntityDamageByEntity(), new onPlayerExpChange(), new onPlayerFish());

        new Metrics(this);

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> getPersist().save(islandManager), 0, 20);

        setupPlaceholderAPI();

        getLogger().info("-------------------------------");
        getLogger().info("");
        getLogger().info(getDescription().getName() + " Enabled!");
        getLogger().info("");
        getLogger().info("-------------------------------");
    }

    public void sendErrorMessage(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        getLogger().info(sw.getBuffer().toString());
        if (configuration == null || configuration.sendErrorReports) {
            getLogger().info("Sending Error Report");
        }
    }

    private void registerListeners(Listener... listener) {
        for (Listener l : listener) {
            Bukkit.getPluginManager().registerEvents(l, this);
        }
    }

    private void setupPlaceholderAPI() {
        Plugin clip = getServer().getPluginManager().getPlugin("PlaceholderAPI");
        if (clip != null && clip.isEnabled()) {
            this.clipPlaceholderAPIManager = new ClipPlaceholderAPIManager();
            if (this.clipPlaceholderAPIManager.register()) {
                getLogger().info("Successfully registered placeholders with PlaceholderAPI.");
            }
        }
    }

    public void loadSchematic() {
        File schematicFolder = new File(getDataFolder(), "schematics");
        if (!schematicFolder.exists()) {
            schematicFolder.mkdir();
        }

        if (!new File(schematicFolder, "island.schematic").exists()) {
            if (getResource("schematics/island.schematic") != null) {
                saveResource("schematics/island.schematic", false);
            }
        }
    }

    public void loadConfigs() {
        configuration = persist.getFile(Config.class).exists() ? persist.load(Config.class) : new Config();
        missions = persist.getFile(Missions.class).exists() ? persist.load(Missions.class) : new Missions();
        islandManager = persist.getFile(IslandManager.class).exists() ? persist.load(IslandManager.class) : new IslandManager();
        messages = persist.getFile(Messages.class).exists() ? persist.load(Messages.class) : new Messages();
        oreGen = persist.getFile(OreGen.class).exists() ? persist.load(OreGen.class) : new OreGen();

        for (Island island : islandManager.islands.values()) {
            island.init();
        }
    }

    public void saveConfigs() {
        persist.save(configuration);
        persist.save(missions);
        persist.save(islandManager);
        persist.save(messages);
        persist.save(oreGen);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        saveConfigs();

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.closeInventory();
        }

        getLogger().info("-------------------------------");
        getLogger().info("");
        getLogger().info(getDescription().getName() + " Disabled!");
        getLogger().info("");
        getLogger().info("-------------------------------");
    }

    public static EpicSkyblock getInstance() {
        return instance;
    }

    public static IslandManager getIslandManager() {
        return islandManager;
    }

    public static Config getConfiguration() {
        return configuration;
    }

    public static Missions getMissions() {
        return missions;
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static Messages getMessages() {
        return messages;
    }

    public static OreGen getOreGen() {
        return oreGen;
    }

    public static Persist getPersist() {
        return persist;
    }
}
