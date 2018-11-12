package ua.training.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DBConnector {
    private static final String DB_CONNECTION_URL = "jdbc:mysql://localhost/task1?user=user&password=pass";

    private static Connection dbConnection;
    private final Logger log = LogManager.getLogger(DBConnector.class);

    public DBConnector() {
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION_URL);
        } catch (SQLException ex) {
            log.error("SQLException: " + ex.getMessage());
            log.error("SQLState: " + ex.getSQLState());
            log.error("VendorError: " + ex.getErrorCode());
            log.error(Arrays.toString(ex.getStackTrace()));
        }
    }

    public void checkDataBaseTable() {
        try (Statement statement = dbConnection.createStatement()) {
            statement.execute(DBQueries.CREATE_TABLE);
        } catch (SQLException ex) {
            log.error("SQLException: " + ex.getMessage());
            log.error("SQLState: " + ex.getSQLState());
            log.error("VendorError: " + ex.getErrorCode());
            log.error(Arrays.toString(ex.getStackTrace()));
        }
    }

    public List<FullContactData> getDataFromTable() {
        List<FullContactData> result = new ArrayList<>();

        try (Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(DBQueries.GET_DATA_FROM_TABLE);
            result = processResultSet(resultSet);
        } catch (SQLException ex) {
            log.error("SQLException: " + ex.getMessage());
            log.error("SQLState: " + ex.getSQLState());
            log.error("VendorError: " + ex.getErrorCode());
            log.error(Arrays.toString(ex.getStackTrace()));
        }

        return result;
    }

    public List<FullContactData> getQueriedDataFromTable(String query) {
        List<FullContactData> result = new ArrayList<>();

        try (Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            result = processResultSet(resultSet);
        } catch (SQLException ex) {
            log.error("SQLException: " + ex.getMessage());
            log.error("SQLState: " + ex.getSQLState());
            log.error("VendorError: " + ex.getErrorCode());
            log.error(Arrays.toString(ex.getStackTrace()));
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
            log.error("SQLException: " + ex.getMessage());
            log.error("SQLState: " + ex.getSQLState());
            log.error("VendorError: " + ex.getErrorCode());
            log.error(Arrays.toString(ex.getStackTrace()));
        }
    }

    public void executeUpdateQuery(String query) {
        try (Statement statement = dbConnection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            log.error("SQLException: " + ex.getMessage());
            log.error("SQLState: " + ex.getSQLState());
            log.error(Arrays.toString(ex.getStackTrace()));
        }
    }
}
