package com.peaches.epicskyblock.listeners;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.Island;
import com.peaches.epicskyblock.User;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class onEntityDeath implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) return;
        Island island = User.getUser(e.getEntity().getKiller().getName()).getIsland();
        if (island.hunter > -1) {
            island.hunter++;
            if (island.hunter >= EpicSkyblock.getMissions().hunter.getAmount()) {
                island.hunter = -1;
                island.completeMission("Hunter", EpicSkyblock.getMissions().hunter.getAmount());
            }
        }
        if (island.getExpBooster() != 0) {
            e.setDroppedExp(e.getDroppedExp() * 2);
        }
    }
}
