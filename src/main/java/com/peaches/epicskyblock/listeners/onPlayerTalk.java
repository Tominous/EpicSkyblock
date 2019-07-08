package com.peaches.epicskyblock.listeners;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.User;
import com.peaches.epicskyblock.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onPlayerTalk implements Listener {

    @EventHandler
    public void onPlayerTalk(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        User u = User.getUser(p.getName());
        if (u.warp != null) {
            if (u.warp.getPassword().equals(e.getMessage())) {
                Bukkit.getScheduler().runTask(EpicSkyblock.getInstance(), () -> p.teleport(u.warp.getLocation()));
                        p.sendMessage(Utils.color(EpicSkyblock.getMessages().teleporting.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
            } else {
                p.sendMessage(Utils.color(EpicSkyblock.getMessages().wrongPassword.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                u.warp = null;
            }
            e.setCancelled(true);
        }
    }
}
