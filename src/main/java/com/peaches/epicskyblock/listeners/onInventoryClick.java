package com.peaches.epicskyblock.listeners;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.NMSUtils;
import com.peaches.epicskyblock.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class onInventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        User user = User.getUser(e.getWhoClicked().getName());
        if (user.getIsland() != null) {
            if (e.getInventory().equals(user.getIsland().getBoosterGUI().inventory)) {
                e.setCancelled(true);
                if (e.getCurrentItem() == null) return;
                if (e.getCurrentItem().equals(user.getIsland().getBoosterGUI().spawner)) {
                    if (user.getIsland().getCrystals() >= EpicSkyblock.getConfiguration().spawnerBoosterCost) {
                        if (user.getIsland().getSpawnerBooster() == 0) {
                            user.getIsland().setCrystals(user.getIsland().getCrystals() - EpicSkyblock.getConfiguration().spawnerBoosterCost);
                            user.getIsland().setSpawnerBooster(3600);
                        } else {
                            e.getWhoClicked().sendMessage("Spawner booster already active");
                        }
                    } else {
                        e.getWhoClicked().sendMessage("You dont have enough island crystals");
                    }
                }
                if (e.getCurrentItem().equals(user.getIsland().getBoosterGUI().farming)) {
                    if (user.getIsland().getCrystals() >= EpicSkyblock.getConfiguration().farmingBoosterCost) {
                        if (user.getIsland().getFarmingBooster() == 0) {
                            user.getIsland().setCrystals(user.getIsland().getCrystals() - EpicSkyblock.getConfiguration().farmingBoosterCost);
                            user.getIsland().setFarmingBooster(3600);
                        } else {
                            e.getWhoClicked().sendMessage("Farming booster already active");
                        }
                    } else {
                        e.getWhoClicked().sendMessage("You dont have enough island crystals");
                    }
                }
                if (e.getCurrentItem().equals(user.getIsland().getBoosterGUI().exp)) {
                    if (user.getIsland().getCrystals() >= EpicSkyblock.getConfiguration().experienceBoosterCost) {
                        if (user.getIsland().getExpBooster() == 0) {
                            user.getIsland().setCrystals(user.getIsland().getCrystals() - EpicSkyblock.getConfiguration().experienceBoosterCost);
                            user.getIsland().setExpBooster(3600);
                        } else {
                            e.getWhoClicked().sendMessage("Exp booster already active");
                        }
                    } else {
                        e.getWhoClicked().sendMessage("You dont have enough island crystals");
                    }
                }
                if (e.getCurrentItem().equals(user.getIsland().getBoosterGUI().flight)) {
                    if (user.getIsland().getCrystals() >= EpicSkyblock.getConfiguration().flightBoosterCost) {
                        if (user.getIsland().getFlightBooster() == 0) {
                            user.getIsland().setCrystals(user.getIsland().getCrystals() - EpicSkyblock.getConfiguration().flightBoosterCost);
                            user.getIsland().setFlightBooster(3600);
                        } else {
                            e.getWhoClicked().sendMessage("Flight booster already active");
                        }
                    } else {
                        e.getWhoClicked().sendMessage("You dont have enough island crystals");
                    }
                }
            }
            if (e.getInventory().equals(user.getIsland().getMissionsGUI().inventory)) {
                e.setCancelled(true);
            }
            if (e.getInventory().equals(user.getIsland().getUpgradeGUI().inventory)) {
                e.setCancelled(true);
                if (e.getCurrentItem().equals(user.getIsland().getUpgradeGUI().size)) {
                    if (EpicSkyblock.getConfiguration().size.containsKey(user.getIsland().getSizeLevel() + 1)) {
                        if (user.getIsland().getCrystals() >= EpicSkyblock.getConfiguration().size.get(user.getIsland().getSizeLevel() + 1).getCost()) {
                            user.getIsland().setCrystals(user.getIsland().getCrystals() - EpicSkyblock.getConfiguration().size.get(user.getIsland().getSizeLevel() + 1).getCost());
                            user.getIsland().setSizeLevel(user.getIsland().getSizeLevel() + 1);
                            NMSUtils.sendWorldBorder(p, NMSUtils.Color.Blue, EpicSkyblock.getConfiguration().size.get(user.getIsland().getSizeLevel()).getSize(), user.getIsland().getCenter());
                        } else {
                            p.sendMessage("You dont have enough Crystals");
                        }
                    } else {
                        p.sendMessage("Maximum Level reached");
                    }
                }
                if (e.getCurrentItem().equals(user.getIsland().getUpgradeGUI().member)) {
                    if (EpicSkyblock.getConfiguration().member.containsKey(user.getIsland().getMemberLevel() + 1)) {
                        if (user.getIsland().getCrystals() >= EpicSkyblock.getConfiguration().member.get(user.getIsland().getMemberLevel() + 1).getCost()) {
                            user.getIsland().setCrystals(user.getIsland().getCrystals() - EpicSkyblock.getConfiguration().member.get(user.getIsland().getMemberLevel() + 1).getCost());
                            user.getIsland().setMemberLevel(user.getIsland().getMemberLevel() + 1);
                        } else {
                            p.sendMessage("You dont have enough Crystals");
                        }
                    } else {
                        p.sendMessage("Maximum Level reached");
                    }
                }
            }
            if (e.getInventory().equals(user.getIsland().getMembersGUI().inventory)) {
                e.setCancelled(true);
            }
        }
    }
}
