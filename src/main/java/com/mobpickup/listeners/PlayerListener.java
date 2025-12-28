package com.mobpickup.listeners;


import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;


public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer(); 
        player.sendMessage("§aMob Bucket plugin enabled :3, ");
        // Handle player join event
    }
    
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity target = event.getRightClicked();
        EntityType type = target.getType();

        // Check player is holding a bucket
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand.getType() != Material.BUCKET) return;

        // Only spawnable mobs
        if (!type.isSpawnable()) return;

        // Remove the mob
        target.remove();

        // Remove one bucket from player's hand
        if (hand.getAmount() > 1) {
            hand.setAmount(hand.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        }

        // Give the spawn egg
        Material eggMaterial = Material.valueOf(type.name() + "_SPAWN_EGG");
        ItemStack egg = new ItemStack(eggMaterial);
        player.getInventory().addItem(egg);

        player.sendMessage("§aYou picked up a §e" + type.name() + " §awith your bucket!");
    }
}
