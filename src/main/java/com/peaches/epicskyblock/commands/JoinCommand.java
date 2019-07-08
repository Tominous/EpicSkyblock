package com.peaches.epicskyblock.commands;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.Island;
import com.peaches.epicskyblock.User;
import com.peaches.epicskyblock.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinCommand extends Command {

    public JoinCommand() {
        super(new ArrayList<>(Arrays.asList("join")),"Join another players island", "", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("/is join <player>");
            return;
        }
        Player p = (Player) sender;
        User user = User.getUser(p.getName());
        if (Bukkit.getPlayer(args[1]) != null) {
            User u = User.getUser(args[1]);
            if (u.getIsland() != null) {
                if (user.getIsland() == null) {
                    Island island = u.getIsland();
                    if (user.invites.contains(island.getId())) {
                        island.addUser(user);
                    } else {
                        sender.sendMessage(Utils.color(EpicSkyblock.getMessages().noActiveInvites.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                    }
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
