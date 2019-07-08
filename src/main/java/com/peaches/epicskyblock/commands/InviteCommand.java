package com.peaches.epicskyblock.commands;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.User;
import com.peaches.epicskyblock.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InviteCommand extends Command {

    public InviteCommand() {
        super(new ArrayList<>(Arrays.asList("invite")), "", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("/is invite <player>");
            return;
        }
        Player p = (Player) sender;
        User user = User.getUser(p.getName());
        Player player = Bukkit.getPlayer(args[1]);
        if (player != null) {
            User u = User.getUser(p.getName());
            if (user.getIsland() != null) {
                if (u.getIsland() == null) {
                    u.invites.add(user.getIsland().getId());
                } else {
                    sender.sendMessage(Utils.color(EpicSkyblock.getMessages().playerAlreadyHaveIsland.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                }
            } else {
                sender.sendMessage(Utils.color(EpicSkyblock.getMessages().noIsland.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
            }
        } else {
            sender.sendMessage(Utils.color(EpicSkyblock.getMessages().playerOffline.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
