package ua.training.service;

import ua.training.model.FullContactData;
import ua.training.util.DBConnector;
import ua.training.util.DBQueries;
import ua.training.util.LocalizationLinks;
import ua.training.util.RegExLinks;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

class Controller {
    private ResourceBundle regexResourceBundle;
    private ResourceBundle messageResourceBundle;
    private List<FullContactData> fullContactDataList;
    private DBConnector dbConnector;

    Controller() {
        regexResourceBundle =
                ResourceBundle.getBundle(RegExLinks.SOURCE.getRegExSource(),
                        new Locale("de"));
        messageResourceBundle =
                ResourceBundle.getBundle(LocalizationLinks.SOURCE.getLocaleSource(),
                        new Locale("de"));

        dbConnector = new DBConnector();
        fullContactDataList = getDataFromDatabase();
    }

    DBConnector getDbConnector() {
        return dbConnector;
    }

    static String getStringFromBundle(ResourceBundle resourceBundle,
                                      String message) {
        return new String(StandardCharsets.ISO_8859_1.encode(resourceBundle.getString(message)).array());
    }

    ResourceBundle getRegexResourceBundle() {
        return regexResourceBundle;
    }

    ResourceBundle getMessageResourceBundle() {
        return messageResourceBundle;
    }

    void setResourceBundles(String locale) {
        this.regexResourceBundle =
                ResourceBundle.getBundle(RegExLinks.SOURCE.getRegExSource(),
                        new Locale(locale));
        this.messageResourceBundle =
                ResourceBundle.getBundle(LocalizationLinks.SOURCE.getLocaleSource(),
                        new Locale(locale));
    }

    List<FullContactData> executeSortingDBQuery(String query) {
        dbConnector.checkDataBaseTable();
        fullContactDataList = dbConnector.getQueriedDataFromTable(query);
        return fullContactDataList;
    }

    List<FullContactData> executeAnyDBQuery(String query) {
        dbConnector.checkDataBaseTable();
        return dbConnector.getQueriedDataFromTable(query);
    }

    List<FullContactData> getDataFromDatabase() {
        dbConnector.checkDataBaseTable();
        fullContactDataList = dbConnector.getDataFromTable();
        return fullContactDataList;
    }

    void addContactToList(FullContactData fullContactData) {
        StringBuilder query = new StringBuilder().append(DBQueries.HEADER_INSERT_TO_DB);
        StringBuilder queryValues = new StringBuilder().append("(");
        if (fullContactData.getName() != null) {
            query.append("name,");
            queryValues.append("'").append(fullContactData.getName()).append("',");
        }
        if (fullContactData.getLastName() != null) {
            query.append("lastname,");
            queryValues.append("'").append(fullContactData.getLastName()).append("',");
        }
        if (fullContactData.getNickname() != null) {
            query.append("nickname,");
            queryValues.append("'").append(fullContactData.getNickname()).append("',");
        }
        if (fullContactData.getPhone() != null) {
            query.append("phone,");
            queryValues.append("'").append(fullContactData.getPhone()).append("',");
        }
        if (fullContactData.getId() != null) {
            query.append("id,");
            queryValues.append("'").append(fullContactData.getId()).append("',");
        }
        if (queryValues.lastIndexOf(",") == queryValues.length() - 1) {
            queryValues.deleteCharAt(queryValues.length() - 1);
        }
        if (query.lastIndexOf(",") == query.length() - 1) {
            query.deleteCharAt(query.length() - 1);
        }
        query.append(")");
        queryValues.append(")");
        dbConnector.executeQuery(query.append(" VALUES ").append(queryValues).toString());

        fullContactDataList = getDataFromDatabase();
    }

    List<FullContactData> getFullContactDataList() {
        return fullContactDataList;
    }

    void setFullContactDataList(List<FullContactData> fullContactDataList) {
        this.fullContactDataList = fullContactDataList;
    }
}
