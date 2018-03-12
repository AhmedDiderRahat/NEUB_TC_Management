package com.neub.touristclub.databaseconnection;

import com.neub.touristclub.constants.Constants;

import java.sql.*;

public class DbConnector {

    public Connection connection;
    public Statement statement;
    public ResultSet resultset;

    public DbConnector() {

        try {
            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.NEUBTC_DB, Constants.NEUBTC_DB_USERNAME, Constants.NEUBTC_DB_PASSWORD);
            statement = connection.createStatement();

        } //end try
        catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
