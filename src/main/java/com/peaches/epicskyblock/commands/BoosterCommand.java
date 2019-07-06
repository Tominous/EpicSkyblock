package com.peaches.epicskyblock.commands;

import com.peaches.epicskyblock.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoosterCommand extends Command {

    public BoosterCommand() {
        super(new ArrayList<>(Arrays.asList("booster")), "", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User user = User.getUser(p.getName());
        if (user.getIsland() != null) {
            p.openInventory(user.getIsland().getBoosterGUI().inventory);
        } else {
            sender.sendMessage("You dont have an island");
        }
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
