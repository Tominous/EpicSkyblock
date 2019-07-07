package com.peaches.epicskyblock.listeners;

import com.peaches.epicskyblock.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class onEntityDamage implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            if (e.getEntity() instanceof Player) {
                User u = User.getUser(e.getEntity().getName());
                if (u.getIsland() != null) {
                    if (u.getIsland().isInIsland(e.getEntity().getLocation())) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
