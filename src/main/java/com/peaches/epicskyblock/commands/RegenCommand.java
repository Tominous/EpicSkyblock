package com.peaches.epicskyblock.commands;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.User;
import com.peaches.epicskyblock.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegenCommand extends Command {

    public RegenCommand() {
        super(new ArrayList<>(Arrays.asList("regen")), "Regenerate your island","", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User user = User.getUser(p.getName());
        if (user.getIsland() != null) {
            if (user.getIsland().getOwner().equals(p.getName())) {
                user.getIsland().generateIsland();
            } else {
                sender.sendMessage(Utils.color(EpicSkyblock.getMessages().mustBeIslandOwner.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
            }
        } else {
            sender.sendMessage(Utils.color(EpicSkyblock.getMessages().noIsland.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
