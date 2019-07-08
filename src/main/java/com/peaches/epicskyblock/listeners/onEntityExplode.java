package com.peaches.epicskyblock.listeners;

import com.peaches.epicskyblock.EpicSkyblock;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class onEntityExplode implements Listener {

    public void onEntityExplode(EntityExplodeEvent e) {
        if (e.getLocation().getWorld().equals(EpicSkyblock.getIslandManager().getWorld())) {
            e.setCancelled(true);
        }
    }
}
