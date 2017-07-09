package fr.paragoumba.trade.Commands;

import com.sun.deploy.util.ArrayUtil;
import fr.paragoumba.trade.DB;
import fr.paragoumba.trade.Trade;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Paragoumba on 06/07/2017.
 */

public class cCommand implements TabExecutor {

    private Trade trade;

    public cCommand(Trade trade) {

        this.trade = trade;

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player){

            Player player = (Player) commandSender;

            if (strings.length > 0) {

                if (strings[0].equalsIgnoreCase("recap")) {

                    List<String> sales = DB.querySales(player);
                    List<String> stock = DB.queryStock(player);

                    player.sendMessage(ChatColor.GOLD + "Trade:");

                    player.sendMessage("- Vos ventes: " + sales + ".");
                    player.sendMessage("- Votre stock: " + stock + ".");
                    player.sendMessage("- Vos revenus: " + stock);

                    return true;

                } else {

                    return false;

                }

            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        String usage = (String) trade.getDescription().getCommands().get("c").get("usage");
        return Arrays.asList(usage.substring(usage.indexOf(" ")).split(" "));
    }
}
