package com.bms.functionality;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnection {
    public static Connection connect() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Properties properties = new Properties();
            properties.load(new FileInputStream("com/bms/functionality/credentials.properties"));

            var jdbcUrl = properties.getProperty("url");
            var user = properties.getProperty("username");
            var password = properties.getProperty("password");

            return DriverManager.getConnection(jdbcUrl, user, password);

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
