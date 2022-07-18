package com.strangeone101.vocalmimicrymenu;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.storage.DBConnection;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseUtil {

    public static String databaseType = ProjectKorra.plugin.getConfig().getString("Storage.engine");

    public static final String TABLE_NAME = "pk_vocalmenu";
    private static final char SPLIT = ';';

    public static void createDB() {
        if (!DBConnection.sql.tableExists(TABLE_NAME)) {
            VocalMimicryMenu.INSTANCE.getLogger().info("Creating database to store vocal menu favorites");

            if (databaseType.equalsIgnoreCase("mysql")) {
                String query = "CREATE TABLE `" + TABLE_NAME + "` (" + "`uuid` varchar(36) NOT NULL," + "`player` varchar(16) NOT NULL," + "`favorites` VARCHAR(512), PRIMARY KEY (uuid));";
                DBConnection.sql.modifyQuery(query, false);
            } else { //SQLite
                String query = "CREATE TABLE `" + TABLE_NAME + "` (" + "`uuid` TEXT(36) PRIMARY KEY," + "`player` TEXT(16)," + "`favorites` TEXT(512));";
                DBConnection.sql.modifyQuery(query, false);
            }

            VocalMimicryMenu.INSTANCE.getLogger().info("Database created!");
        }
    }

    public static String[] getFavorites(Player player) {
        ResultSet rs2 = DBConnection.sql.readQuery("SELECT * FROM " + TABLE_NAME + " WHERE uuid = '" + player.getUniqueId() + "'");
        try {
            if (rs2.next()) {
                String favorites = rs2.getString("favorites");

                return favorites.split(String.valueOf(SPLIT));
            } else {
                return new String[0];
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    public static void setFavorites(Player player, String[] favorites) {
        ResultSet rs2 = DBConnection.sql.readQuery("SELECT * FROM " + TABLE_NAME + " WHERE uuid = '" + player.getUniqueId() + "'");

        try {
            String favs = String.join(String.valueOf(SPLIT), favorites);
            if (rs2.next()) {
                DBConnection.sql.modifyQuery("UPDATE " + TABLE_NAME + " SET favorites = '" + favs + "' WHERE uuid = '" + player.getUniqueId() + "'");
                DBConnection.sql.modifyQuery("UPDATE " + TABLE_NAME + " SET player = '" + player.getName() + "' WHERE uuid = '" + player.getUniqueId() + "'");
            } else {
                DBConnection.sql.modifyQuery("INSERT INTO " + TABLE_NAME + " (uuid, player, favorites) VALUES ('" + player.getUniqueId() + "', '" + player.getName() + "', '" + favs + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
