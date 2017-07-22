package fr.paragoumba.trade.Events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Paragoumba on 14/07/2017.
 */

public class onInventoryOpenEvent implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event){

        Inventory inv = event.getInventory();

        if (inv.getType().equals(InventoryType.CHEST)){

            for (int i = 0; i < inv.getSize(); ++i){

                ItemStack item = new ItemStack(Material.ANVIL);

                inv.setItem(i, item);

            }
        }
    }
}
