package com.peaches.epicskyblock.listeners;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class onPlayerMove implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getTo().getWorld().equals(EpicSkyblock.getIslandManager().getWorld())) {
            if (e.getTo().getY() <= 0) {
                // Send to island home
                Player p = e.getPlayer();
                User u = User.getUser(p.getName());
                if (u.getIsland() != null) {
                    u.getIsland().teleportHome(p);
                }
            }
        }
    }

}
