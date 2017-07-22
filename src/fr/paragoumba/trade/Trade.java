package fr.paragoumba.trade;

import fr.paragoumba.trade.Commands.cCommand;
import fr.paragoumba.trade.Events.onPlayerInteractEvent;
import fr.paragoumba.trade.Events.onPlayerInteractsAtEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created by Paragoumba on 05/07/2017.
 */

public class Trade extends JavaPlugin {

    public static Trade plugin;

    @Override
    public void onEnable() {

        File config = new File(getDataFolder(), "config.yml");

        try {
            if (!config.exists()) {

                getConfig().options().copyDefaults(true);
                saveConfig();
                saveDefaultConfig();

            }
        } catch (Exception e) {

            e.printStackTrace();

        }

        plugin = this;

        DB.connect();

        //Commands
        getCommand("c").setExecutor(new cCommand());

        //Events
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new onPlayerInteractEvent(), this);
        pm.registerEvents(new onPlayerInteractsAtEntityEvent(), this);

    }

    @Override
    public void onDisable() {

        DB.disconnect();

    }
}
