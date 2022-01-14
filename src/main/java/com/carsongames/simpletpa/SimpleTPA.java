package com.carsongames.simpletpa;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/*
 *  Implements the TPA and TPAACCEPT commands
 */
public final class SimpleTPA extends JavaPlugin {
    static int cooldown = 30; // Determines the cooldown of the tpa request
    Player player ;
    public static Plugin plugin;
    @Override public void onEnable() {
        plugin = this;
        getLogger().info("Started!");
        this.getCommand("tpa").setExecutor(new tpa());
        this.getCommand("tpaaccept").setExecutor(new tpaaccept());
        Cooldown_Data.setupCooldown();
        Cooldown_Data.CheckMap();


    }

    @Override public void onDisable() { }

    /*
     * tpaaccept command
     */
    public class tpaaccept implements CommandExecutor {
        Player playerinit;
        @Override public boolean onCommand(CommandSender sender, Command        command, String label, String[] args) {
            Player player = (Player)sender;
            try {
                if (Map.playermap.containsKey(player.getName())) {
                    String init = Map.playermap.get(player.getName());
                    sender.sendMessage(ChatColor.BLUE + "Sending " + init + " to you!");
                    Map.playermap.remove(player.getName());
                    Cooldown_Data.cooldowns.remove(player.getUniqueId());
                    playerinit = Bukkit.getPlayer(init);
                    playerinit.sendMessage(net.md_5.bungee.api.ChatColor.BLUE + player.getName() + " has accepted!");
                    playerinit.teleport(player.getLocation());
                }
                else{
                    sender.sendMessage(net.md_5.bungee.api.ChatColor.BLUE +"No requests!");
                }
            }
            catch (NullPointerException e){
                sender.sendMessage(net.md_5.bungee.api.ChatColor.BLUE +"Player not online!");
                e.printStackTrace();
            }
            return false;
        }
    }

    public class tpa implements CommandExecutor {
        @Override 
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (args.length == 1) {
                Player playerinit = (Player) sender;
                String playername = args[0];
                try {
                    /*.getPlayer(playername) returns a name that starts with
                    playername, so "s" would return "Saws", making the catch
                    not work correctly. Fixed */
                    player = Bukkit.getServer().getPlayerExact(playername);
                    if (!Cooldown_Data.checkCooldown(player)) {
                        sender.sendMessage(ChatColor.BLUE + "You have already sent this player a request.");
                        return true;
                    }
                }
                catch (NullPointerException e){
                    sender.sendMessage(ChatColor.BLUE + playername + " is offline.");
                    return true;
                }

                if(playername.equals(playerinit.getName())){
                    sender.sendMessage(ChatColor.BLUE + "Can't tpa to yourself!");
                    return true;
                }
                Cooldown_Data.setCooldown(player, cooldown);
                Map.playermap.put(playername, playerinit.getName());
                player.sendMessage(ChatColor.BLUE + playerinit.getName() + " would like to teleport to your location! Type /tpaaccept to allow. You have " + cooldown + " seconds to respond.");
                sender.sendMessage(ChatColor.BLUE + "Request Sent!");
                return true;
            } 
            else {
                return false;
            }
        }
    }
}



