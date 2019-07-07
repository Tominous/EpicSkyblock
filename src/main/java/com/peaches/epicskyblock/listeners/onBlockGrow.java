package com.peaches.epicskyblock.listeners;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.Island;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.material.Crops;

public class onBlockGrow implements Listener {

    @EventHandler
    public void onBlockGrow(BlockGrowEvent e) {
        Island island = EpicSkyblock.getIslandManager().getIslandViaLocation(e.getBlock().getLocation());
        if (island != null) {
            if (island.getFarmingBooster() != 0) {
                if (e.getBlock().getType().equals(Material.CROPS)) {
                    e.setCancelled(true);
                    Crops c = new Crops(CropState.RIPE);
                    BlockState bs = e.getBlock().getState();
                    bs.setData(c);
                    bs.update();
                }
            }
        }
    }
}
