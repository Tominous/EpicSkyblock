package com.peaches.epicskyblock.commands;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.User;
import com.peaches.epicskyblock.Utils;
import com.peaches.epicskyblock.gui.TopGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TopCommand extends Command {

    public TopCommand() {
        super(new ArrayList<>(Arrays.asList("top")), "View the top islands", "", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User user = User.getUser(p.getName());
        if (user.getIsland() != null) {
            p.openInventory(TopGUI.inventory);
        } else {
            p.sendMessage(Utils.color(EpicSkyblock.getMessages().noIsland.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
        }

    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
