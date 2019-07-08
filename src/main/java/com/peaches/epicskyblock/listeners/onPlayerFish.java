package com.peaches.epicskyblock.listeners;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class onPlayerFish implements Listener {

    @EventHandler
    public void onPlayerFish(PlayerFishEvent e) {
        if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            User u = User.getUser(e.getPlayer().getName());
            if (u.getIsland() != null) {
                if (u.getIsland().fisherman > -1) {
                    u.getIsland().fisherman++;
                    if (u.getIsland().fisherman >= EpicSkyblock.getMissions().fisherman.getAmount()) {
                        u.getIsland().fisherman = -1;
                        u.getIsland().completeMission("Fisherman", EpicSkyblock.getMissions().fisherman.getAmount());
                    }
                }
            }
        }
    }
}
