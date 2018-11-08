package ua.training.service;

import ua.training.model.FullContactData;
import ua.training.util.DBConnector;
import ua.training.util.DBQueries;
import ua.training.util.LocalizationLinks;
import ua.training.util.RegExLinks;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller {
    private ResourceBundle regexResourceBundle;
    private ResourceBundle messageResourceBundle;
    private List<FullContactData> fullContactDataList;
    private DBConnector dbConnector;

    public Controller() {
        regexResourceBundle =
                ResourceBundle.getBundle(RegExLinks.SOURCE.getRegExSource(),
                        new Locale("en"));
        messageResourceBundle =
                ResourceBundle.getBundle(LocalizationLinks.SOURCE.getLocaleSource(),
                        new Locale("en"));

        dbConnector = new DBConnector();
        fullContactDataList = getDataFromDatabase();

    }

    public static String getStringFromBundle(ResourceBundle resourceBundle,
                                             String message) {
        return resourceBundle.getString(message);
    }

    public ResourceBundle getRegexResourceBundle() {
        return regexResourceBundle;
    }

    public ResourceBundle getMessageResourceBundle() {
        return messageResourceBundle;
    }

    public void setResourceBundles(String locale) {
        this.regexResourceBundle =
                ResourceBundle.getBundle(RegExLinks.SOURCE.getRegExSource(),
                        new Locale(locale));
        this.messageResourceBundle =
                ResourceBundle.getBundle(LocalizationLinks.SOURCE.getLocaleSource(),
                        new Locale(locale));
    }

    public List<FullContactData> executeDBQuery(String query) {
        dbConnector.checkDataBaseTable();
        return dbConnector.getSortedDataFromTable(query);
    }

    public List<FullContactData> getDataFromDatabase() {
        dbConnector.checkDataBaseTable();
        return dbConnector.getDataFromTable();
    }

    public void addContactToList(FullContactData fullContactData) {
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
        queryValues.append(");");
        System.out.println(query.append(" VALUES ").append(queryValues).toString());
        dbConnector.executeQuery(query.append(" VALUES ").append(queryValues).toString());
    }

    public List<FullContactData> getFullContactDataList() {
        return fullContactDataList;
    }

    public void setFullContactDataList(List<FullContactData> fullContactDataList) {
        this.fullContactDataList = fullContactDataList;
    }
}
