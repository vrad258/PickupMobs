package com.mobpickup.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;

import com.mobpickup.PickupMobs;

public class mobbucketCommand implements CommandExecutor {

    private final PickupMobs plugin;

    public mobbucketCommand(PickupMobs plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        // Check if player is holding a bucket
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand.getType() != Material.BUCKET) {
            player.sendMessage("§cYou must be holding a bucket to use this command.");
            return true;
        }

        double range = 5.0;

        RayTraceResult result = player.getWorld().rayTraceEntities(
                player.getEyeLocation(),
                player.getEyeLocation().getDirection(),
                range,
                entity -> entity != player
        );

        if (result == null || result.getHitEntity() == null) {
            player.sendMessage("§cYou are not looking at any entity.");
            return true;
        }

        Entity target = result.getHitEntity();
        EntityType type = target.getType();

        // Only spawnable mobs
        if (!type.isSpawnable()) {
            player.sendMessage("§cYou cannot get a spawn egg for that entity.");
            return true;
        }

        // Create the spawn egg
        Material eggMaterial = Material.valueOf(type.name() + "_SPAWN_EGG");
        ItemStack egg = new ItemStack(eggMaterial);

        // Remove one bucket from player's hand
        if (hand.getAmount() > 1) {
            hand.setAmount(hand.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        }

        target.remove();


        // Give the spawn egg
        player.getInventory().addItem(egg);
        player.sendMessage("§aYou used a bucket and received a §e" + type.name() + " §aspawn egg!");

        return true;
    }
}
