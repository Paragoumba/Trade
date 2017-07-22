package fr.paragoumba.trade.Commands;

import fr.paragoumba.trade.DB;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

import static fr.paragoumba.trade.Trade.plugin;

/**
 * Created by Paragoumba on 06/07/2017.
 */

public class cCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player){

            Player player = (Player) commandSender;

            if (strings.length > 0) {

                if (strings[0].equalsIgnoreCase("recap")) {

                    HashMap<ItemStack, HashMap<Double, Integer>> sales = DB.querySales(player.getUniqueId());
                    List<ItemStack> stock = DB.queryStock(player.getUniqueId());
                    double recipe = 0;
                    StringBuilder salesBuilder = new StringBuilder();
                    StringBuilder stockBuilder = new StringBuilder();

                    for (Map.Entry<ItemStack, HashMap<Double, Integer>> gains : sales.entrySet()){

                        salesBuilder.append(gains.getKey().getType());
                        salesBuilder.append(" : ");

                        for (Map.Entry<Double, Integer> gains2 : gains.getValue().entrySet()){

                            int itemsNumber = gains2.getValue();
                            double price = gains2.getKey();

                            salesBuilder.append(itemsNumber);
                            salesBuilder.append(" pour ");
                            salesBuilder.append(price);
                            salesBuilder.append("€");

                            recipe += itemsNumber * price;

                        }
                    }

                    for (ItemStack item : stock){

                        stockBuilder.append(item.getAmount());
                        stockBuilder.append(" ");
                        stockBuilder.append(item.getType().name());

                    }

                    player.sendMessage(ChatColor.GOLD + "Trade:");

                    player.sendMessage("- Vos ventes: " + salesBuilder + ".");
                    player.sendMessage("- Votre stock: " + stockBuilder + ".");
                    player.sendMessage("- Vos revenus: " + recipe);

                    return true;

                } else if (strings[0].equalsIgnoreCase("stock")){

                    Yaml yaml = new Yaml();
                    ItemStack item = new ItemStack(Material.ANVIL);
                    List<ItemStack> stock = DB.queryStock(player.getUniqueId());

                    item.setAmount(10);
                    stock.add(0, item);
                    DB.updateStock(player.getUniqueId(), yaml.dump(stock));

                } else if (strings[0].equalsIgnoreCase("sales")){

                    Yaml yaml = new Yaml();
                    ItemStack item = new ItemStack(Material.ANVIL);
                    HashMap<ItemStack, HashMap<Double, Integer>> sales = DB.querySales(player.getUniqueId());
                    HashMap<Double, Integer> prices = sales.get(item);

                    item.setAmount(1);
                    prices.put(1.5, 10);
                    sales.put(item, prices);
                    DB.updateStock(player.getUniqueId(), yaml.dump(sales));

                } else if (strings[0].equalsIgnoreCase("test")) {

                    Yaml yaml = new Yaml();

                    System.out.println("Welcome to Shop Simulator 2017.");

                    HashMap<ItemStack, HashMap<Double, Integer>> sales = new HashMap<>();
                    HashMap<Double, Integer> prices = new HashMap<>();

                    System.out.println(sales);

                    ItemStack item = new ItemStack(Material.ANVIL);

                    item.setAmount(1);
                    prices.put(0.5, 5);
                    sales.put(item, prices);

                    System.out.println(sales);

                    item.setAmount(2);
                    prices.clear();
                    prices.put(1.5, 15);

                    System.out.println(sales);

                    System.out.println((HashMap<ItemStack, HashMap<Double, Integer>>) yaml.load(yaml.dump(sales)));

                } else {

                    return false;

                }
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings){

        String usage = (String) plugin.getDescription().getCommands().get("c").get("usage");
        return Arrays.asList(usage.substring(usage.indexOf(" ")).split(" "));
    }
}
