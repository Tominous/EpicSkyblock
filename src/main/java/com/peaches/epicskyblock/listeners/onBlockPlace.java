package com.peaches.epicskyblock.listeners;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.Island;
import com.peaches.epicskyblock.User;
import com.peaches.epicskyblock.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class onBlockPlace implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        User u = User.getUser(e.getPlayer().getName());
        if (e.getBlock().getLocation().getWorld().equals(EpicSkyblock.getIslandManager().getWorld())) {
            Island island = u.getIsland();
            if (island != null) {
                if (island.builder > -1) {
                    island.builder++;
                    if (island.builder >= EpicSkyblock.getMissions().builder.getAmount()) {
                        island.builder = -1;
                        island.completeMission("Builder", EpicSkyblock.getMissions().builder.getReward());
                    }
                }
                if (island.isInIsland(e.getBlock().getLocation())) {
                    if (Utils.isBlockValuable(e.getBlock())) {
                        if (!island.blocks.contains(e.getBlock().getLocation()))
                            island.blocks.add(e.getBlock().getLocation());
                    }
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
