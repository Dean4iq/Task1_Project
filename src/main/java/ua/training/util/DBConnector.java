package ua.training.util;

import ua.training.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DBConnector {
    private static final String DB_CONNECTION_URL = "jdbc:mysql://localhost/task1?user=user&password=pass";

    private static Connection dbConnection;

    public DBConnector() {
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION_URL);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public void checkDataBaseTable() {
        try (Statement statement = dbConnection.createStatement()) {
            statement.execute(DBQueries.CREATE_TABLE);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public List<FullContactData> getDataFromTable() {
        List<FullContactData> result = new ArrayList<>();

        try (Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(DBQueries.GET_DATA_FROM_TABLE);
            while (resultSet.next()) {
                result.add(new FullContactData(
                        new NameContact(resultSet.getString("name")),
                        new LastNameContact(resultSet.getString("lastname")),
                        new NicknameContact(resultSet.getString("nickname")),
                        new PhoneContact(resultSet.getString("phone")),
                        new IdContact(resultSet.getString("id"))));
                result.get(result.size()-1).setRowId(resultSet.getInt("db_id"));
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return result;
    }

    public List<FullContactData> getSortedDataFromTable(String query) {
        List<FullContactData> result = new ArrayList<>();

        try (Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result.add(new FullContactData(
                        new NameContact(resultSet.getString("name")),
                        new LastNameContact(resultSet.getString("lastname")),
                        new NicknameContact(resultSet.getString("nickname")),
                        new PhoneContact(resultSet.getString("phone")),
                        new IdContact(resultSet.getString("id"))));
                result.get(result.size()-1).setRowId(resultSet.getInt("db_id"));
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return result;
    }

    public void executeQuery(String query){
        try (Statement statement = dbConnection.createStatement()) {
            statement.execute(query);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
