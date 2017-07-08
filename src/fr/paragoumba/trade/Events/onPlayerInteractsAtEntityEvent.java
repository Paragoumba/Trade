package fr.paragoumba.trade.Events;

import fr.paragoumba.trade.DB;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Paragoumba on 06/07/2017.
 */

public class onPlayerInteractsAtEntityEvent implements Listener {

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event){

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (entity instanceof NPC && DB.) {

            Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST, "Trade");
            ArrayList<ItemStack> items = new ArrayList<>();

            for (int i = 0; i < inventory.getSize() - 1; ++i) {
                items.add(i, inventory.getItem(i));
            }

            DB.updateStock(player, items.toString());

        }

    }
}
