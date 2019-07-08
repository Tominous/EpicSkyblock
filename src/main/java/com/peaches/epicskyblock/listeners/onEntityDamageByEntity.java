package com.peaches.epicskyblock.listeners;

import com.peaches.epicskyblock.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class onEntityDamageByEntity implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (e.getEntity() instanceof Player) {
                Player player = (Player) e.getEntity();
                User damager = User.getUser(p.getName());
                User entity = User.getUser(player.getName());
                if (damager.getIsland().equals(entity.getIsland()) && damager.getIsland() != null) {
                    e.setCancelled(true);
                    p.sendMessage("You cannot attack your island members");
                }
            }
        }
    }
}
