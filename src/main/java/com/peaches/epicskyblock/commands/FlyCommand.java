package com.peaches.epicskyblock.commands;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlyCommand extends Command {

    public FlyCommand() {
        super(new ArrayList<>(Arrays.asList("fly", "flight")), "", true);
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
                    p.sendMessage("Your flight as been disabled");
                } else {
                    p.setAllowFlight(true);
                    p.setFlying(true);
                    p.sendMessage("Your flight as been enabled");
                }
            } else {
                p.sendMessage("Your island booster is not active");
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
