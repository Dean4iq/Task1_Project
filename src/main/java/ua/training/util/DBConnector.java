package ua.training.util;

import com.mysql.jdbc.ResultSetImpl;
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
            result = processResultSet(resultSet);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return result;
    }

    public List<FullContactData> getQueriedDataFromTable(String query) {
        List<FullContactData> result = new ArrayList<>();

        try (Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            result = processResultSet(resultSet);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return result;
    }

    private List<FullContactData> processResultSet(ResultSet resultSet)
            throws SQLException {
        List<FullContactData> result = new ArrayList<>();

        while (resultSet.next()) {
            result.add(new FullContactData());

            resultSet.getString("name");
            if (!resultSet.wasNull()) {
                result.get(result.size() - 1)
                        .setName(new NameContact(
                                resultSet.getString("name")));
            }
            resultSet.getString("lastname");
            if (!resultSet.wasNull()) {
                result.get(result.size() - 1)
                        .setLastName(new LastNameContact(
                                resultSet.getString("lastname")));
            }
            resultSet.getString("nickname");
            if (!resultSet.wasNull()) {
                result.get(result.size() - 1)
                        .setNickname(new NicknameContact(
                                resultSet.getString("nickname")));
            }
            resultSet.getString("phone");
            if (!resultSet.wasNull()) {
                result.get(result.size() - 1)
                        .setPhone(new PhoneContact(
                                resultSet.getString("phone")));
            }
            resultSet.getString("id");
            if (!resultSet.wasNull()) {
                result.get(result.size() - 1)
                        .setId(new IdContact(
                                resultSet.getString("id")));
            }
            result.get(result.size() - 1).setRowId(resultSet.getInt("db_id"));
        }

        return result;
    }

    public void executeDeleteQuery(String query) {
        try (Statement statement = dbConnection.createStatement()) {
            statement.execute(query);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public void executeUpdateQuery(String query) {
        try (Statement statement = dbConnection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println(query);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
