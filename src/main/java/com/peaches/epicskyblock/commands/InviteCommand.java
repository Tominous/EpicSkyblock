package com.peaches.epicskyblock.commands;

import com.peaches.epicskyblock.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InviteCommand extends Command {

    public InviteCommand() {
        super(new ArrayList<>(Arrays.asList("invite")), "", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("/is invite <player>");
            return;
        }
        Player p = (Player) sender;
        User user = User.getUser(p.getName());
        User u = User.getUser(args[1]);
        if (user.getIsland() != null) {
            if (u.getIsland() == null) {
                u.invites.add(user.getIsland().getId());
            } else {
                sender.sendMessage("Player already has an island");
            }
        } else {
            sender.sendMessage("You dont have an island");
        }
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
