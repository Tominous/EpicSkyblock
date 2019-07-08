package com.peaches.epicskyblock.placeholders;


import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.User;
import com.peaches.epicskyblock.Utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class ClipPlaceholderAPIManager extends PlaceholderExpansion {

    // Identifier for this expansion
    @Override
    public String getIdentifier() {
        return "epicskyblock";
    }

    @Override
    public String getAuthor() {
        return "Peaches_MLG";
    }

    // Since we are registering this expansion from the dependency, this can be null
    @Override
    public String getPlugin() {
        return null;
    }

    // Return the plugin version since this expansion is bundled with the dependency
    @Override
    public String getVersion() {
        return EpicSkyblock.getInstance().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String placeholder) {
        if (player == null || placeholder == null) {
            return "";
        }

        User user = User.getUser(player.getName());

        switch (placeholder) {
            case "island_value":
                return user.getIsland() != null ? user.getIsland().getValue() + "" : "N/A";
            case "island_rank":
                return user.getIsland() != null ? Utils.getIslandRank(user.getIsland()) + "" : "N/A";
            case "island_owner":
                return user.getIsland() != null ? user.getIsland().getOwner() : "N/A";
            case "island_crystals":
                return user.getIsland() != null ? user.getIsland().getCrystals() + "" : "N/A";
        }

        return null;
    }
}