package com.peaches.epicskyblock.commands;

import com.peaches.epicskyblock.EpicSkyblock;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class Command {
    private final boolean player;
    private final List<String> aliases;
    private final String permission;
    private final String description;

    public Command(List<String> aliases, String description, String permission, boolean player) {
        this.aliases = aliases;
        this.permission = permission;
        this.player = player;
        this.description = description;
        EpicSkyblock.getCommandManager().registerCommand(this);
    }

    public boolean isPlayer() {
        return player;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getPermission() {
        return permission;
    }

    public abstract void execute(CommandSender sender, String[] args);

    public abstract List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args);

    public String getDescription() {
        return description;
    }
}
