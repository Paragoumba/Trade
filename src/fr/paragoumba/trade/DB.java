package fr.paragoumba.trade;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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

    public static void updateSales(UUID uuid, Object update) {

        try(Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = state.executeQuery("SELECT * FROM `Trade` WHERE trader='" + uuid + "'")) {

            result.updateObject("sales", update);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public static void updateStock(UUID uuid, Object update) {

        try(Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = state.executeQuery("SELECT * FROM `Trade` WHERE trader='" + uuid + "'")) {

            result.updateObject("stock", update);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public static HashMap<ItemStack, HashMap<Double, Integer>> querySales(UUID uuid) {

        try(Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT sales FROM Trade WHERE trader = '" + uuid + "'")) {

            result.next();

            Object r = result.getObject("sales");

            return (HashMap<ItemStack, HashMap<Double, Integer>>) r;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return new HashMap<>();
    }

    public static List<ItemStack> queryStock(UUID uuid) {

        try(Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT stock FROM Trade WHERE trader = '" + uuid + "'")) {

            result.next();

            return (List<ItemStack>) result.getObject("stock");

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

    private static void reset() {

        try (Statement state = connection.createStatement()) {

            state.executeUpdate("DELETE FROM Tickets");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
