package com.peaches.epicskyblock.commands;

import com.peaches.epicskyblock.EpicSkyblock;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeCommand extends Command {

    public HomeCommand() {
        super(new ArrayList<>(Arrays.asList("home")), "EpicSkyblock.home", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (EpicSkyblock.getIslandManager().islands.containsKey(p.getName())) {
            EpicSkyblock.getIslandManager().islands.get(p.getName()).teleportHome(p);
        } else {
            sender.sendMessage("You dont have an island");
        }
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
