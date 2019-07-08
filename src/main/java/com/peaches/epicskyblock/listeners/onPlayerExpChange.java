package com.peaches.epicskyblock.listeners;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class onPlayerExpChange implements Listener {

    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent e) {
        Player p = e.getPlayer();
        User user = User.getUser(p.getName());
        if (user.getIsland() != null) {
            if (user.getIsland().treasureHunter > -1) {
                user.getIsland().treasureHunter += e.getAmount();
                if (user.getIsland().treasureHunter >= EpicSkyblock.getMissions().treasureHunter.getAmount()) {
                    user.getIsland().treasureHunter = -1;
                    user.getIsland().completeMission("Treasure Hunter", EpicSkyblock.getMissions().treasureHunter.getAmount());
                }
            }
        }
    }
}
