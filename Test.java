package com.implementable.test;

import com.implementable.test.events.CasualEvents;
import com.implementable.test.handlers.VanishHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static com.implementable.test.utilities.ChatUtil.translate;

/*
 * Developed by Implementable 2019/08/29
 */
public class Test extends JavaPlugin {
    private VanishHandler vanishHandler;

    @Override
    public void onEnable() {
        /* Class Instances */
        this.vanishHandler = new VanishHandler();

        /* Command Registration */
        this.getCommand("vanish").setExecutor((sender, $, label, args) -> {
            if (!(sender instanceof Player)) return true;

            final Player player = (Player) sender;
            if (player.hasPermission("test.vanish")) {
                vanishHandler.toggleVanish(player);
            } else {
                player.sendMessage(translate("&cInsufficient permission."));
            }
            return true;
        });

        /* Event Registration */
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new CasualEvents(this), this);
    }

    public VanishHandler getVanishHandler() {
        return vanishHandler;
    }
}
