package com.peaches.epicskyblock.listeners;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.Island;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;

public class onSpawnerSpawn implements Listener {

    @EventHandler
    public void onSpawnerSpawn(SpawnerSpawnEvent e) {
        Island island = EpicSkyblock.getIslandManager().getIslandViaLocation(e.getLocation());
        if (island.getSpawnerBooster() != 0) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(EpicSkyblock.getInstance(), () -> e.getSpawner().setDelay(e.getSpawner().getDelay() / 2), 0);
        }
    }

}
