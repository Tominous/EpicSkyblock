package com.peaches.epicskyblock.commands;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super(new ArrayList<>(Arrays.asList("reload")), "EpicSkyblock.reload", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        EpicSkyblock.getInstance().loadConfigs();
        sender.sendMessage("Configs reloaded");
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
