package com.peaches.epicskyblock.commands;

import com.peaches.epicskyblock.Island;
import com.peaches.epicskyblock.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinCommand extends Command {

    public JoinCommand() {
        super(new ArrayList<>(Arrays.asList("join")), "", false);
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
                        island.teleportHome(p);
                        user.invites.clear();
                    } else {
                        sender.sendMessage("You dont have any active invites for this island");
                    }
                } else {
                    sender.sendMessage("Player already has an island");
                }
            } else {
                sender.sendMessage("You dont have an island");
            }
        } else {
            sender.sendMessage("That player is not currently online");
        }
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
