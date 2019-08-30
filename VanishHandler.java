package com.implementable.test.handlers;

import com.sun.istack.internal.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.implementable.test.utilities.ChatUtil.translate;

/*
 * Developed by Implementable 2019/08/29
 */
public class VanishHandler {
    private final List<Player> vanishedPlayers;

    /**
     * Class constructor.
     */
    public VanishHandler() {
        /* Initializations */
        this.vanishedPlayers = new ArrayList<>();
    }

    /**
     * @param player
     * Toggle a player's vanish mode.
     */
    public void toggleVanish(Player player) {
        if (!isVanished(player)) {
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.hidePlayer(player));
            player.sendMessage(translate("&7Your vanish has been &aenabled&7."));
            vanishedPlayers.add(player);
        } else {
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.showPlayer(player));
            player.sendMessage(translate("&7Your vanish has been &cdisabled&7."));
            vanishedPlayers.remove(player);
        }
    }

    /**
     * @param player
     * @return
     * Check if vanishedPlayers list contains the parameter player.
     */
    public boolean isVanished(Player player) {
        return vanishedPlayers.contains(player);
    }

    /**
     * @return
     * Get all of the vanished players.
     */
    @NotNull
    public List<Player> getVanishedPlayers() {
        return vanishedPlayers;
    }
}