package com.peaches.epicskyblock.commands;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.User;
import com.peaches.epicskyblock.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValueCommand extends Command {

    public ValueCommand() {
        super(new ArrayList<>(Arrays.asList("value")), "Shows your island value", "", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User user = User.getUser(p.getName());
        if (user.getIsland() != null) {
            p.sendMessage(Utils.color(EpicSkyblock.getMessages().islandValue.replace("%value%", user.getIsland().getValue() + "").replace("%rank%", Utils.getIslandRank(user.getIsland()) + "").replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
        } else {
            p.sendMessage(Utils.color(EpicSkyblock.getMessages().noIsland.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
        }

    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
