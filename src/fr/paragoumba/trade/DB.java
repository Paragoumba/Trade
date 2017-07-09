package fr.paragoumba.trade;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public static void updateStock(Player trader, String update) {

        try(Statement state = connection.createStatement()) {

            state.executeUpdate("UPDATE Trade SET stock = '" + update + "' WHERE trader = '" + trader.getUniqueId() + "'");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public static List<String> querySales(Player trader) {

        try(Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT sales FROM Trade WHERE trader = '" + trader.getUniqueId() + "'")) {

            String sales = "";

            while (result.next()){

                sales = result.getString("sales");

            }

            return Arrays.asList(sales.substring(1, sales.length() - 1).split(","));

        } catch (Exception e) {

            e.printStackTrace();

        }

        return new ArrayList<>();
    }

    public static List<String> queryStock(Player trader) {

        try(Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT stock FROM Trade WHERE trader = '" + trader.getUniqueId() + "'")) {

            String stock = "";

            while (result.next()){

                stock = result.getString("stock");

            }

            return Arrays.asList(stock.substring(1, stock.length() - 1).split(","));

        } catch (Exception e) {

            e.printStackTrace();

        }

        return new ArrayList<>();
    }

    public static boolean traderExists(Player trader){

        try(Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT trader FROM Trade WHERE trader = '" + trader.getUniqueId() + "'")) {

            result.next();

            return result.getString("trader").equals(trader.getUniqueId().toString());

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    public static void connect() {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, login, password);
            Bukkit.getLogger().log(Level.WARNING, "Trade: Database connected. (" + url + ")");

        } catch (Exception e) {

            Bukkit.getLogger().log(Level.WARNING, "Trade: Error in database connection. (" + url + ")");
            e.printStackTrace();

        }
    }

    public static void disconnect() {

        if (connection != null) {

            try {

                connection.close();
                Bukkit.getLogger().log(Level.WARNING, "Trade: Database disconnected.");

            } catch (SQLException e) {

                Bukkit.getLogger().log(Level.WARNING, "Trade: Error in database disconnection.");
                e.printStackTrace();

            }

        }
    }

    public static void reset() {

        try (Statement state = connection.createStatement()) {

            state.executeUpdate("DELETE FROM Tickets");

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }
}
