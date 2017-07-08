package fr.paragoumba.trade.Events;

import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Paragoumba on 05/07/2017.
 */

public class onPlayerInteractEvent implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){

        if (event.getClickedBlock() instanceof Chest){



        } else if (event.getClickedBlock() instanceof Sign){



        }
    }
}
