package com.peaches.epicskyblock.commands;

import com.peaches.epicskyblock.EpicSkyblock;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateCommand extends Command {

    public CreateCommand() {
        super(new ArrayList<>(Arrays.asList("create")), "EpicSkyblock.create", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (EpicSkyblock.getIslandManager().islands.containsKey(p.getName())) {
            sender.sendMessage("You already have an island");
        } else {
            EpicSkyblock.getIslandManager().createIsland(p);
        }
    }
}
