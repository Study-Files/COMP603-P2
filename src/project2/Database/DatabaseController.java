package project2.Database;

import project2.Pets.Pet;
import project2.Pets.Types.*;

import java.sql.*;
import java.util.HashSet;

public class DatabaseController {

    private Connection connection;

    public DatabaseController() {
        try {
            String URL = "jdbc:derby:PetGame;create=true";
            connection = DriverManager.getConnection(URL);
            createTables();
        } catch (SQLException e) {
            System.out.println("Error connecting to database");
            System.exit(500);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error closing connection");
            e.printStackTrace();
        }
    }

    private void createTables() {
        if (!checkTableExists("pets")) {
            System.out.println("Creating pets table");
            try {
                Statement statement = connection.createStatement();
                String sql = "CREATE TABLE pets (" +
                        "USERNAME VARCHAR(255) NOT NULL," +
                        "NAME VARCHAR(255) NOT NULL," +
                        "TYPE VARCHAR(255) NOT NULL," +
                        "AGE INTEGER," +
                        "HAPPINESS INTEGER," +
                        "HUNGER INTEGER," +
                        "HEALTH INTEGER," +
                        "HATCH_TIME INTEGER" +
                        ")";
                statement.executeUpdate(sql);
                statement.close();
            } catch (SQLException e) {
                System.out.println("Error creating table 'pets'");
            }
        }
        if (!checkTableExists("users")) {
            System.out.println("Creating users table");
            try {
                Statement statement = connection.createStatement();
                String sql = "CREATE TABLE users (" +
                        "USERNAME VARCHAR(255) NOT NULL," +
                        "PASSWORD VARCHAR(255) NOT NULL," +
                        "PRIMARY KEY (USERNAME)" +
                        ")";
                statement.executeUpdate(sql);
                statement.close();
            } catch (SQLException e) {
                System.out.println("Error creating table 'users'");
            }
        }
        if (!checkTableExists("cycles")) {
            System.out.println("Creating cycles table");
            try {
                Statement statement = connection.createStatement();
                String sql = "CREATE TABLE cycles (" +
                        "USERNAME VARCHAR(255) NOT NULL," +
                        "PET_NAME VARCHAR(255) NOT NULL," +
                        "CYCLE INTEGER NOT NULL," +
                        "TOTAL_CYCLES INTEGER NOT NULL," +
                        "YEARS INTEGER NOT NULL" +
                        ")";
                statement.executeUpdate(sql);
                statement.close();
            } catch (SQLException e) {
                System.out.println("Error creating table 'cycles'");
            }
        }
    }


    public boolean checkTableExists(String name) {
        boolean flag = false;
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);
            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(name) == 0) {
                    flag = true;
                }
            }
            rsDBMeta.close();
        } catch (SQLException ignored) {
        }
        return flag;
    }




}
