package com.implementable.test.events;

import com.implementable.test.Test;
import com.implementable.test.handlers.VanishHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

import static com.implementable.test.utilities.ChatUtil.translate;

/*
 * Developed by Implementable 2019/08/29
 */
public class CasualEvents implements Listener {
    private final Test test;
    private final VanishHandler vanishHandler;
    private final List<Player> itemsPickup;
    private final BukkitScheduler scheduler = Bukkit.getScheduler();

    /**
     * @param test
     * Constructor passing through the main class (Test)
     */
    public CasualEvents(Test test) {
        this.test = test;
        this.vanishHandler = test.getVanishHandler();
        this.itemsPickup = new ArrayList<>();
    }

    @EventHandler
    private void onConnect(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (vanishHandler.isVanished(onlinePlayer)) {
                player.hidePlayer(onlinePlayer);
            }
        });
    }

    @EventHandler
    private void onDisconnect(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        if (vanishHandler.isVanished(player)) {
            vanishHandler.toggleVanish(player);
        }
    }

    @EventHandler
    private void onChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        /* Check if player is in vanish mode */
        if (vanishHandler.isVanished(player)) {
            /* Cancel event and send a message */
            event.setCancelled(true);
            player.sendMessage(translate("&cYou may not chat while in vanish!"));
        }
    }

    @EventHandler
    private void onPlace(BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        /* Check if player is in vanish mode */
        if (vanishHandler.isVanished(player)) {
            /* Cancel event and send a message */
            event.setCancelled(true);
            player.sendMessage(translate("&cYou may not place blocks while in vanish!"));
        }
    }

    @EventHandler
    private void onBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        /* Check if player is in vanish mode */
        if (vanishHandler.isVanished(player)) {
            /* Cancel event and send a message */
            event.setCancelled(true);
            player.sendMessage(translate("&cYou may not break blocks while in vanish!"));
        }
    }

    @EventHandler
    private void onAttack(EntityDamageByEntityEvent event) {
        /* Check if damager is not a player return if true */
        if (!(event.getDamager() instanceof Player)) return;

        final Player player = (Player) event.getDamager();
        /* Check if player is in vanish mode */
        if (vanishHandler.isVanished(player)) {
            /* Cancel event and send a message */
            event.setCancelled(true);
            player.sendMessage(translate("&cYou may not attack other entities while in vanish!"));
        }
    }

    @EventHandler
    private void onTarget(EntityTargetEvent event) {
        /* Check if target is not a player return if true */
        if (!(event.getTarget() instanceof Player)) return;

        final Player player = (Player) event.getTarget();
        /* Check if player is in vanish mode, if true cancel event */
        if (vanishHandler.isVanished(player)) event.setCancelled(true);
    }

    @EventHandler
    private void onPickup(EntityPickupItemEvent event) {
        /* Check if entity is not a player return if true */
        if (!(event.getEntity() instanceof Player)) return;

        final Player player = (Player) event.getEntity();
        /* Check if player is in vanish mode */
        if (vanishHandler.isVanished(player)) {
            /* Cancel event and send a message if they're not in a separate list */
            event.setCancelled(true);
            if (!itemsPickup.contains(player)) {
                player.sendMessage(translate("&cYou may not pick up items while in vanish!"));
                itemsPickup.add(player);
                scheduler.runTaskLater(test, () -> itemsPickup.remove(player), 20L*10);
            }
        }
    }
}