package com.peaches.epicskyblock.listeners;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.Island;
import com.peaches.epicskyblock.User;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class onBlockBreak implements Listener {

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        User u = User.getUser(e.getPlayer().getName());
        if (e.getBlock().getLocation().getWorld().equals(EpicSkyblock.getIslandManager().getWorld())) {
            Island island = u.getIsland();
            if (island != null) {
                if (e.getBlock().getType().name().endsWith("ORE")) {
                    if (u.getIsland().miner > -1) {
                        u.getIsland().miner++;
                        if (u.getIsland().miner >= EpicSkyblock.getMissions().miner.getAmount()) {
                            u.getIsland().miner = -1;
                            u.getIsland().completeMission("Miner", EpicSkyblock.getMissions().miner.getAmount());
                        }
                    }
                }
                if (e.getBlock().getType().equals(Material.CROPS)) {
                    if (u.getIsland().farmer > -1) {
                        u.getIsland().farmer++;
                        if (u.getIsland().farmer >= EpicSkyblock.getMissions().farmer.getAmount()) {
                            u.getIsland().farmer = -1;
                            u.getIsland().completeMission("Farmer", EpicSkyblock.getMissions().farmer.getAmount());
                        }
                    }
                }
                if (island.isInIsland(e.getBlock().getLocation())) {
                    island.blocks.remove(e.getBlock().getLocation());
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
