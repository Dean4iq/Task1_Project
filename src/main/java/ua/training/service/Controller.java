package ua.training.service;

import ua.training.model.FullContactData;
import ua.training.util.DBConnector;
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

    List<FullContactData> executeSelectDBQuery(String query) {
        dbConnector.checkDataBaseTable();
        fullContactDataList = dbConnector.getQueriedDataFromTable(query);
        return fullContactDataList;
    }

    void executeDeleteQuery(String query){
        dbConnector.executeDeleteQuery(query);
    }

    void executeUpdateDBQuery(String query) {
        dbConnector.checkDataBaseTable();
        dbConnector.executeUpdateQuery(query);
    }

    List<FullContactData> getDataFromDatabase() {
        dbConnector.checkDataBaseTable();
        fullContactDataList = dbConnector.getDataFromTable();
        return fullContactDataList;
    }

    void addContactToList(String query) {
        dbConnector.executeUpdateQuery(query);

        fullContactDataList = getDataFromDatabase();
    }

    List<FullContactData> getFullContactDataList() {
        return fullContactDataList;
    }

    void setFullContactDataList(List<FullContactData> fullContactDataList) {
        this.fullContactDataList = fullContactDataList;
    }
}
