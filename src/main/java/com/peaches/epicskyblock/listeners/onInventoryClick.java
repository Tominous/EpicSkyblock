package com.peaches.epicskyblock.listeners;

import com.peaches.epicskyblock.*;
import com.peaches.epicskyblock.gui.TopGUI;
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
                            e.getWhoClicked().sendMessage(Utils.color(EpicSkyblock.getMessages().spawnerBoosterActive.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                        }
                    } else {
                        e.getWhoClicked().sendMessage(Utils.color(EpicSkyblock.getMessages().notEnoughCrystals.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                    }
                }
                if (e.getCurrentItem().equals(user.getIsland().getBoosterGUI().farming)) {
                    if (user.getIsland().getCrystals() >= EpicSkyblock.getConfiguration().farmingBoosterCost) {
                        if (user.getIsland().getFarmingBooster() == 0) {
                            user.getIsland().setCrystals(user.getIsland().getCrystals() - EpicSkyblock.getConfiguration().farmingBoosterCost);
                            user.getIsland().setFarmingBooster(3600);
                        } else {
                            e.getWhoClicked().sendMessage(Utils.color(EpicSkyblock.getMessages().farmingBoosterActive.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                        }
                    } else {
                        e.getWhoClicked().sendMessage(Utils.color(EpicSkyblock.getMessages().notEnoughCrystals.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                    }
                }
                if (e.getCurrentItem().equals(user.getIsland().getBoosterGUI().exp)) {
                    if (user.getIsland().getCrystals() >= EpicSkyblock.getConfiguration().experienceBoosterCost) {
                        if (user.getIsland().getExpBooster() == 0) {
                            user.getIsland().setCrystals(user.getIsland().getCrystals() - EpicSkyblock.getConfiguration().experienceBoosterCost);
                            user.getIsland().setExpBooster(3600);
                        } else {
                            e.getWhoClicked().sendMessage(Utils.color(EpicSkyblock.getMessages().expBoosterActive.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                        }
                    } else {
                        e.getWhoClicked().sendMessage(Utils.color(EpicSkyblock.getMessages().notEnoughCrystals.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                    }
                }
                if (e.getCurrentItem().equals(user.getIsland().getBoosterGUI().flight)) {
                    if (user.getIsland().getCrystals() >= EpicSkyblock.getConfiguration().flightBoosterCost) {
                        if (user.getIsland().getFlightBooster() == 0) {
                            user.getIsland().setCrystals(user.getIsland().getCrystals() - EpicSkyblock.getConfiguration().flightBoosterCost);
                            user.getIsland().setFlightBooster(3600);
                        } else {
                            e.getWhoClicked().sendMessage(Utils.color(EpicSkyblock.getMessages().flightBoosterActive.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                        }
                    } else {
                        e.getWhoClicked().sendMessage(Utils.color(EpicSkyblock.getMessages().notEnoughCrystals.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
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
                            user.getIsland().sendBorder();
                        } else {
                            e.getWhoClicked().sendMessage(Utils.color(EpicSkyblock.getMessages().notEnoughCrystals.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                        }
                    } else {
                        p.sendMessage(Utils.color(EpicSkyblock.getMessages().maxLevelReached.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                    }
                }
                if (e.getCurrentItem().equals(user.getIsland().getUpgradeGUI().member)) {
                    if (EpicSkyblock.getConfiguration().member.containsKey(user.getIsland().getMemberLevel() + 1)) {
                        if (user.getIsland().getCrystals() >= EpicSkyblock.getConfiguration().member.get(user.getIsland().getMemberLevel() + 1).getCost()) {
                            user.getIsland().setCrystals(user.getIsland().getCrystals() - EpicSkyblock.getConfiguration().member.get(user.getIsland().getMemberLevel() + 1).getCost());
                            user.getIsland().setMemberLevel(user.getIsland().getMemberLevel() + 1);
                        } else {
                            e.getWhoClicked().sendMessage(Utils.color(EpicSkyblock.getMessages().notEnoughCrystals.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                        }
                    } else {
                        p.sendMessage(Utils.color(EpicSkyblock.getMessages().maxLevelReached.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                    }
                }
                if (e.getCurrentItem().equals(user.getIsland().getUpgradeGUI().warp)) {
                    if (EpicSkyblock.getConfiguration().warp.containsKey(user.getIsland().getWarpLevel() + 1)) {
                        if (user.getIsland().getCrystals() >= EpicSkyblock.getConfiguration().warp.get(user.getIsland().getWarpLevel() + 1).getCost()) {
                            user.getIsland().setCrystals(user.getIsland().getCrystals() - EpicSkyblock.getConfiguration().warp.get(user.getIsland().getWarpLevel() + 1).getCost());
                            user.getIsland().setWarpLevel(user.getIsland().getWarpLevel() + 1);
                        } else {
                            e.getWhoClicked().sendMessage(Utils.color(EpicSkyblock.getMessages().notEnoughCrystals.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                        }
                    } else {
                        p.sendMessage(Utils.color(EpicSkyblock.getMessages().maxLevelReached.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                    }
                }
            }
            if (e.getInventory().equals(user.getIsland().getMembersGUI().inventory)) {
                e.setCancelled(true);
            }
            if (e.getInventory().equals(TopGUI.inventory)) {
                e.setCancelled(true);
            }
            if (e.getInventory().equals(user.getIsland().getBorderColorGUI().inventory)) {
                e.setCancelled(true);
                if (e.getCurrentItem().equals(user.getIsland().getBorderColorGUI().blue))
                    user.getIsland().setBorderColor(NMSUtils.Color.Blue);
                if (e.getCurrentItem().equals(user.getIsland().getBorderColorGUI().red))
                    user.getIsland().setBorderColor(NMSUtils.Color.Red);
                if (e.getCurrentItem().equals(user.getIsland().getBorderColorGUI().green))
                    user.getIsland().setBorderColor(NMSUtils.Color.Green);
                user.getIsland().sendBorder();
            }
            if (e.getInventory().equals(user.getIsland().getWarpGUI().inventory)) {
                e.setCancelled(true);
                if (user.getIsland().getWarpGUI().warps.containsKey(e.getSlot())) {
                    Island.Warp warp = user.getIsland().getWarpGUI().warps.get(e.getSlot());
                    if (warp.getPassword().isEmpty()) {
                        p.teleport(warp.getLocation());
                        p.sendMessage(Utils.color(EpicSkyblock.getMessages().teleporting.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                    } else {
                        p.sendMessage(Utils.color(EpicSkyblock.getMessages().enterPassword.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                        user.warp = warp;
                    }
                    p.closeInventory();
                }
            }
        }
    }
}
