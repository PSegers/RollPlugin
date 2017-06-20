package com.gmail.pieterjansegers1.rollplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

/**
 * Created by Chryomdar on 14/06/2017.
 */

public class CommandRoll implements CommandExecutor {
    private Random random = new Random();
    Main plugin;

    // This is required or else you get NullException
    public CommandRoll(Main instance) {
        this.plugin = instance;
    }

    String nini = "";

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player)sender;
            int arg1;
            int arg3;
            int result;

            if(args.length == 0 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
                player.sendRawMessage(ChatColor.GOLD + "Example 1: " + ChatColor.ITALIC + ChatColor.WHITE + "/roll d20");
                player.sendRawMessage(ChatColor.GOLD + "This will roll a 20-faced die.");
                player.sendRawMessage(ChatColor.GOLD + "Example 2: " + ChatColor.ITALIC + ChatColor.WHITE + "/roll d6 + 3");
                player.sendRawMessage(ChatColor.GOLD + "This will roll a 6-faced die, with an additional 3 added on top.");
                player.sendRawMessage(ChatColor.GOLD + "Spaces are important! The \"d\" is optional.");
                return true;
            } else if(args[0].equalsIgnoreCase("nini")) {
                player.sendRawMessage(ChatColor.GOLD + nini);
                return true;
            } else if(args[0].contains("d")) {
                try {
                    arg1 = Integer.parseInt(args[0].substring(1, args[0].length()));
                } catch (NumberFormatException ex) {
                    player.sendRawMessage(ChatColor.RED + "Please type a valid value after \"roll d\". This must be a number.");
                    return false;
                }
            } else {
                try {
                    arg1 = Integer.parseInt(args[0]);
                } catch (NumberFormatException ex) {
                    player.sendRawMessage(ChatColor.RED + "Please type a valid value after \"roll\". This must be a number.");
                    return false;
                }
            }

            if(args.length == 1) {
                result = this.random.nextInt(arg1);
                this.sendRollMessage(player, result, arg1);
                return true;
            } else if(args[1].equals("+") && !args[2].isEmpty()) {
                try {
                    arg3 = Integer.parseInt(args[2]);
                } catch (NumberFormatException ex) {
                    player.sendRawMessage(ChatColor.RED + "Please type a valid value after \"+\". This must be a number.");
                    return false;
                }

                result = this.random.nextInt(arg1) + arg3;
                this.sendRollMessage(player, result, arg1, arg3);
                return true;
            } else if (args[1].equals("-") && !args[2].isEmpty()) {
                try {
                    arg3 = Integer.parseInt(args[2]);
                } catch (NumberFormatException ex) {
                    player.sendRawMessage(ChatColor.RED + "Please type a valid value after \"-\". This must be a number.");
                    return false;
                }

                result = this.random.nextInt(arg1) - arg3;
                this.sendRollMessage(player, result, arg1, arg3);
                return true;
            } else {
                player.sendRawMessage(ChatColor.RED + "Please utilize the correct syntax.");
                return false;
            }
        }
        return false;
    }

    private boolean sendRollMessage(Player player, int result, int arg1) {
        int radius = this.plugin.getConfig().getInt("roll-radius");
        player.sendRawMessage(ChatColor.AQUA + player.getDisplayName() + " rolled " + ChatColor.WHITE + result + ChatColor.AQUA + " [d" + arg1 + "].");
        List<Entity> eList = player.getNearbyEntities(radius, radius, radius);
        for (Entity e : eList) {
            if(e instanceof Player) {
                e.sendMessage(ChatColor.AQUA + player.getDisplayName() + " rolled " + ChatColor.WHITE + result + ChatColor.AQUA + " [d" + arg1 + "].");
            }
        }
        return true;
    }

    private boolean sendRollMessage(Player player, int result, int arg1, int arg2) {
        int radius = this.plugin.getConfig().getInt("roll-radius");
        player.sendMessage(ChatColor.AQUA + player.getDisplayName() + " rolled " + ChatColor.WHITE + result + ChatColor.AQUA + " [d" + arg1 + "+" + arg2 + "].");
        List<Entity> eList = player.getNearbyEntities(radius, radius, radius);
        for (Entity e : eList) {
            if(e instanceof Player) {
                e.sendMessage(ChatColor.AQUA + player.getDisplayName() + " rolled " + ChatColor.WHITE + result + ChatColor.AQUA + " [d" + arg1 + "+" + arg2 + "].");
            }
        }
        return true;
    }
}
