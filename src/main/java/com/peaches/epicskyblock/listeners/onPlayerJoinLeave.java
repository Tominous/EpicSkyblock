package com.peaches.epicskyblock.listeners;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.Island;
import com.peaches.epicskyblock.NMSUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onPlayerJoinLeave implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Island island = EpicSkyblock.getIslandManager().getIslandViaLocation(e.getPlayer().getLocation());
        if (island != null) {
            NMSUtils.sendWorldBorder(e.getPlayer(), NMSUtils.Color.Blue, EpicSkyblock.getConfiguration().size / 2, island.getCenter());
        }
    }
}
