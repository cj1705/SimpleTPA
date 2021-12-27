package com.carsongames.simpletpa;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class Cooldown_Data {
    public static HashMap<UUID,Double> cooldowns;
    public static void setupCooldown(){
        cooldowns = new HashMap<>();
    }
    public static void setCooldown(Player player, int seconds ){
        double delay = System.currentTimeMillis() + (seconds * 1000L);
        cooldowns.put(player.getUniqueId(), delay);

    }
    public static int getCooldown(Player player){
        return Math.toIntExact(Math.round((cooldowns.get(player.getUniqueId()) - System.currentTimeMillis())/1000));
    }

    public static boolean checkCooldown(Player player) {
        if (!cooldowns.containsKey(player.getUniqueId()) || cooldowns.get(player.getUniqueId()) <= System.currentTimeMillis()) {
            return true;
        }




        return false;
    }
    public static void CheckMap(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(SimpleTPA.plugin, new Runnable() {
            @Override
            public void run() {
                for (UUID uuid :cooldowns.keySet()
                ) {

                            Player player = Bukkit.getPlayer(uuid);


                          if(checkCooldown(player)){
                              String player2string = Map.playermap.get(player.getName());
                            Player player2 = Bukkit.getPlayer(player2string);
                             player.sendMessage(ChatColor.BLUE+"TPA Expired");
                              player2.sendMessage(ChatColor.BLUE+"TPA Expired");

                             cooldowns.remove(uuid);


                             Map.playermap.remove(player.getName());

                         }

                      }
                   }

        }, 0L, 20L);
  

    }



}



