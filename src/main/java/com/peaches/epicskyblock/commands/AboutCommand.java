package com.peaches.epicskyblock.commands;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.User;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AboutCommand extends Command {

    public AboutCommand() {
        super(new ArrayList<>(Arrays.asList("about", "version")), "", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Plugin Name: &7EpicSkyblock"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Plugin Version: &7" + EpicSkyblock.getInstance().getDescription().getVersion()));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Plugin Author: &7Peaches_MLG"));
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
