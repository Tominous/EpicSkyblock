package com.peaches.epicskyblock.commands;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.User;
import com.peaches.epicskyblock.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlyCommand extends Command {

    public FlyCommand() {
        super(new ArrayList<>(Arrays.asList("fly", "flight")),"Toggle your ability to fly", "", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User user = User.getUser(p.getName());
        if (user.getIsland() != null) {
            if (user.getIsland().getFlightBooster() != 0 || p.hasPermission("EpicSkyblock.Fly")) {
                if (p.isFlying()) {
                    p.setAllowFlight(false);
                    p.setFlying(false);
                    p.sendMessage(Utils.color(EpicSkyblock.getMessages().flightDisabled.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                } else {
                    p.setAllowFlight(true);
                    p.setFlying(true);
                    p.sendMessage(Utils.color(EpicSkyblock.getMessages().flightEnabled.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                }
            } else {
                p.sendMessage(Utils.color(EpicSkyblock.getMessages().flightBoosterNotActive.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
            }
        } else {
            EpicSkyblock.getIslandManager().createIsland(p);
        }
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
