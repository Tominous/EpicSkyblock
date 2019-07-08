package com.peaches.epicskyblock.commands;

import com.peaches.epicskyblock.EpicSkyblock;
import com.peaches.epicskyblock.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {
    private HashMap<String, com.peaches.epicskyblock.commands.Command> commands = new HashMap<>();
    private List<com.peaches.epicskyblock.commands.Command> commandList = new ArrayList<>();

    public CommandManager(String command) {
        EpicSkyblock.getInstance().getCommand(command).setExecutor(this);
        EpicSkyblock.getInstance().getCommand(command).setTabCompleter(this);
    }

    public void registerCommands() {
        new CreateCommand();
        new HomeCommand();
        new DeleteCommand();
        new ReloadCommand();
        new RegenCommand();
        new InviteCommand();
        new JoinCommand();
        new MissionsCommand();
        new UpgradeCommand();
        new BoosterCommand();
        new CrystalsCommand();
        new GiveCrystalsCommand();
        new MembersCommand();
        new FlyCommand();
        new AboutCommand();
        new WarpsCommand();
        new SetWarpCommand();
        new ValueCommand();
        new TopCommand();
        new LeaveCommand();
        new WorldBorderCommand();
    }

    public void registerCommand(com.peaches.epicskyblock.commands.Command command) {
        for (String alias : command.getAliases()) {
            commands.put(alias.toLowerCase(), command);
        }
        commandList.add(command);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {
        if (args.length != 0) {
            if (commands.containsKey(args[0])) {
                com.peaches.epicskyblock.commands.Command command = commands.get(args[0]);
                if (!command.isPlayer() || cs instanceof Player) {
                    if (cs.hasPermission(command.getPermission()) || command.getPermission().isEmpty()) {
                        command.execute(cs, args);
                        return true;
                    } else {
                        // No permission
                        cs.sendMessage(Utils.color(EpicSkyblock.getMessages().noPermission.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                        return true;
                    }
                } else {
                    // Must be a player
                    cs.sendMessage(Utils.color(EpicSkyblock.getMessages().mustBeAPlayer.replace("%prefix%", EpicSkyblock.getConfiguration().prefix)));
                    return true;
                }
            }
        }
        //Help Menu
        cs.sendMessage(Utils.color("&b&lEpicSkyblock: &bHelp"));
        for (com.peaches.epicskyblock.commands.Command c : commandList) {
            cs.sendMessage(Utils.color("&b&l * &7" + c.getAliases().get(0) + ": &b" + c.getDescription()));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String s, String[] args) {
        if (args.length == 1) {
            ArrayList<String> result = new ArrayList<>();
            for (String command : commands.keySet()) {
                if (command.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(command);
                }
            }
            return result;
        }
        if (commands.containsKey(args[0])) {
            return commands.get(args[0]).TabComplete(cs, cmd, s, args);
        }
        return null;
    }
}
