package fr.paragoumba.trade;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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

        try (Statement state = connection.createStatement()) {

            state.executeUpdate("UPDATE Trade SET sales = '" + update + "' WHERE trader = '" + trader.getUniqueId() + "'");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public static void updateStock(Player trader, String update) {

        try (Statement state = connection.createStatement()) {

            state.executeUpdate("UPDATE Trade SET stock = '" + update + "' WHERE trader = '" + trader.getUniqueId() + "'");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public static boolean traderExists(Player trader){

        try (Statement state = connection.createStatement()) {

            state.executeUpdate("SELECT * FROM Trade WHERE trader = '" + trader.getUniqueId() + "'");

        } catch (Exception e) {

            e.printStackTrace();

        }
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
