package com.peaches.epicskyblock.commands;

import com.peaches.epicskyblock.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetWarpCommand extends Command {

    public SetWarpCommand() {
        super(new ArrayList<>(Arrays.asList("setwarp", "addwarp")), "", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length == 2 || args.length == 3) {
            User user = User.getUser(p.getName());
            if (user.getIsland() != null) {
                String password = args.length == 3 ? args[2] : "";
                user.getIsland().addWarp(p, p.getLocation(), args[1], password);
            } else {
                sender.sendMessage("You dont have an island");
            }
        } else {
            p.sendMessage("/is setwarp <Name> (Password)");
        }
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
