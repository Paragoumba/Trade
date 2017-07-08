package fr.paragoumba.trade.Events;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Paragoumba on 06/07/2017.
 */

public class onBlockPlaceEvent implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){

        Block block = event.getBlock();

        if (block instanceof Sign){

            Sign sign = (Sign) block;
            String[] lines = sign.getLines();

            for (String line : lines){

                if (line.equals("trade")){

                }
                
            }
        }
    }
}
