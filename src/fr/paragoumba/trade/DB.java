package fr.paragoumba.trade;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.Yaml;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import static fr.paragoumba.trade.Trade.plugin;

/**
 * Created by Paragoumba on 08/07/2017.
 */

public class DB {

    private DB() {}

    private static Configuration config = plugin.getConfig();
    private static Connection connection;
    private static String url = "jdbc:mysql://" + config.getString("host") + ":" + config.getString("port") + "/" + config.getString("database");
    private static String login = config.getString("login");
    private static String password = config.getString("password");

    public static void updateSales(Player trader, String update) {

        try(Statement state = connection.createStatement()) {

            state.executeUpdate("UPDATE Trade SET sales = '" + update + "' WHERE trader = '" + trader.getUniqueId() + "'");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public static void updateStock(UUID uuid, String update) {

        try(Statement state = connection.createStatement()) {

            state.executeUpdate("UPDATE Trade SET stock = '" + update + "' WHERE trader = '" + uuid + "'");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public static HashMap<ItemStack, HashMap<Double, Integer>> querySales(UUID uuid) {

        try(Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT sales FROM Trade WHERE trader = '" + uuid + "'")) {

            Yaml yaml = new Yaml();

            result.next();

            return (HashMap<ItemStack, HashMap<Double, Integer>>) yaml.load(result.getString("sales"));

        } catch (Exception e) {

            e.printStackTrace();

        }

        return new HashMap<>();
    }

    public static List<ItemStack> queryStock(UUID uuid) {

        try(Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT stock FROM Trade WHERE trader = '" + uuid + "'")) {

            Yaml yaml = new Yaml();

            result.next();

            return (List<ItemStack>) yaml.load(result.getString("stock"));

        } catch (Exception e) {

            e.printStackTrace();

        }

        return new ArrayList<>();
    }

    public static boolean traderExists(UUID uuid){

        try(Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT trader FROM Trade WHERE trader = '" + uuid + "'")) {

            result.next();

            return result.getString("trader").equals(uuid.toString());

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    static void connect() {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, login, password);
            Bukkit.getLogger().log(Level.WARNING, "Trade: Database connected. (" + url + ")");

        } catch (Exception e) {

            Bukkit.getLogger().log(Level.WARNING, "Trade: Error in database connection. (" + url + ")");
            e.printStackTrace();

        }
    }

    static void disconnect() {

        if (connection != null) {

            try {

                connection.close();
                Bukkit.getLogger().log(Level.WARNING, "Trade: Database disconnected.");

            } catch (Exception e) {

                Bukkit.getLogger().log(Level.WARNING, "Trade: Error in database disconnection.");
                e.printStackTrace();

            }

        }
    }

    static void reset() {

        try (Statement state = connection.createStatement()) {

            state.executeUpdate("DELETE FROM Tickets");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
