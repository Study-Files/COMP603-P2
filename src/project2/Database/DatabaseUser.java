package project2.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUser {

    public static boolean login(Connection connection, String username, String password) {
        boolean flag = false;
        try {
            ResultSet rs = connection.prepareStatement("SELECT * FROM USERS WHERE USERNAME = '" + username + "'").executeQuery();
            if (rs.next()) {
                if (rs.getString("PASSWORD").equals(password)) {
                    flag = true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error logging in");
        }
        return flag;
    }

    public static boolean register(Connection connection, String username, String password) {
        boolean flag = false;
        try {
            ResultSet rs = connection.prepareStatement("SELECT * FROM USERS WHERE USERNAME = '" + username + "'").executeQuery();
            if (!rs.next()) {
                connection.prepareStatement("INSERT INTO USERS (USERNAME, PASSWORD) VALUES ('" + username + "', '" + password + "')").executeUpdate();
                flag = true;
            }
        } catch (SQLException e) {
            System.out.println("Error registering");
        }
        return flag;
    }
}
