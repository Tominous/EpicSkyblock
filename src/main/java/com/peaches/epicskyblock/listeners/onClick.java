package com.peaches.epicskyblock.listeners;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.Island;
import com.peaches.epicskyblock.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class onClick implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getAction().name().contains("BLOCK")) {
            User u = User.getUser(e.getPlayer().getName());
            if (e.getClickedBlock().getLocation().getWorld().equals(EpicSkyblock.getIslandManager().getWorld())) {
                Island island = u.getIsland();
                if (island != null) {
                    if ((e.getClickedBlock().getX() > island.getPos1().getX() && e.getClickedBlock().getX() <= island.getPos2().getX()) && (e.getClickedBlock().getZ() > island.getPos1().getZ() && e.getClickedBlock().getZ() <= island.getPos2().getZ())) {
                        // Block is in players island
                    } else {
                        e.setCancelled(true);
                    }
                } else {
                    e.setCancelled(true);
                }
            }
        }
    }
}
