package net.tnn1nja.backwalks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.lang.Math;

public final class BackWalks extends JavaPlugin implements Listener, CommandExecutor{

    @Override
    public void onEnable(){
        Bukkit.getLogger().info("[BackWalks] Successfully Loaded!");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Location f = e.getFrom();
        Location t = e.getTo();

        float yaw = p.getLocation().getYaw();
        double[] vYaw = vYawCalc(f, t);

        if (vYaw[1] == 1){
            double diff = Math.abs((vYaw[0]+180) - (yaw+180));
            if (diff > 180){
                diff = 360-diff;
            }
            double bDiff = Math.abs(diff-180);
            if (bDiff > 20){
                if (!(p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR))){
                    p.setHealth(0);
                }
            }
        }
    }

    public double[] vYawCalc(Location from, Location to){
        double[] vector = {to.getX() - from.getX(), to.getZ() - from.getZ()};
        double yawMove = 1;
        double vYaw = 0;
        double conTan = Math.atan(Math.abs(vector[0])/Math.abs(vector[1]));
        double conTanTwo = Math.atan(Math.abs(vector[1])/Math.abs(vector[0]));

        if (vector[1] == 0 && vector[0] == 0){
            yawMove = 0;
        }else if (vector[1] > 0){
            if (vector[0] > 0){
                vYaw = conTan*-57.2958;
            }else if (vector[0] < 0){
                vYaw = conTan*57.2958;
            }else{
                vYaw = 0;
            }
        }else if (vector[1] < 0){
            if (vector[0] > 0){
                vYaw = conTanTwo*-57.2958 - 90;
            }else if (vector[0] < 0){
                vYaw = conTanTwo*57.2958 + 90;
            }else{
                vYaw = 180;
            }
        }else{
            if (vector[0] > 0){
                vYaw = -90;
            }else if (vector[0] < 0){
                vYaw = 90;
            }
        }

        return new double[]{vYaw, yawMove};
    }
}