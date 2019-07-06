package com.peaches.epicskyblock;

import com.peaches.epicskyblock.commands.CommandManager;
import com.peaches.epicskyblock.configs.Config;
import com.peaches.epicskyblock.configs.Messages;
import com.peaches.epicskyblock.serializer.Persist;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class EpicSkyblock extends JavaPlugin {

    private static EpicSkyblock instance;

    private static Config configuration;
    private static Messages messages;
    private static Persist persist;

    private static IslandManager islandManager;

    private static CommandManager commandManager;

    public static Persist getPersist() {
        return persist;
    }

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

        getLogger().info("-------------------------------");
        getLogger().info("");
        getLogger().info(getDescription().getName() + " Enabled!");
        getLogger().info("");
        getLogger().info("-------------------------------");
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
        if (persist.getFile(Config.class).exists()) {
            configuration = persist.load(Config.class);
        } else {
            configuration = new Config();
        }
        if (persist.getFile(IslandManager.class).exists()) {
            islandManager = persist.load(IslandManager.class);
        } else {
            islandManager = new IslandManager();
        }
        if (persist.getFile(Messages.class).exists()) {
            messages = persist.load(Messages.class);
        } else {
            messages = new Messages();
        }
    }

    public void saveConfigs() {
        persist.save(configuration);
        persist.save(islandManager);
        persist.save(messages);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        saveConfigs();

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

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static Messages getMessages() {
        return messages;
    }
}
