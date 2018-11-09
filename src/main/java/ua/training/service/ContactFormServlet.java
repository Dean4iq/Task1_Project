package ua.training.service;

import ua.training.model.*;
import ua.training.util.DBQueries;
import ua.training.util.LocalizationLinks;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ContactFormServlet extends HttpServlet {
    private static Controller controller;

    @Override
    public void init() throws ServletException {
        controller = new Controller();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        req.setAttribute("contacts", controller.getFullContactDataList());
        req.setAttribute("tableDescription",
                new String(StandardCharsets.ISO_8859_1.encode(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.TABLE_DESCRIPTION.getLocaleSource())).array()));
        req.setAttribute("nameColumn",
                new String(StandardCharsets.ISO_8859_1.encode(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.NAME_COLUMN.getLocaleSource())).array()));
        req.setAttribute("lastNameColumn",
                new String(StandardCharsets.ISO_8859_1.encode(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.LASTNAME_COLUMN.getLocaleSource())).array()));
        req.setAttribute("nicknameColumn",
                new String(StandardCharsets.ISO_8859_1.encode(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.NICKNAME_COLUMN.getLocaleSource())).array()));
        req.setAttribute("phoneColumn",
                new String(StandardCharsets.ISO_8859_1.encode(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.PHONE_COLUMN.getLocaleSource())).array()));
        req.setAttribute("idColumn",
                new String(StandardCharsets.ISO_8859_1.encode(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.ID_COLUMN.getLocaleSource())).array()));
        req.setAttribute("inputDeclaration",
                new String(StandardCharsets.ISO_8859_1.encode(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.INPUT_DECLARATION.getLocaleSource())).array()));

        req.getRequestDispatcher("/view/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        if (req.getParameter("addValue") != null) {
            addValueToList(req);
        }
        if (req.getParameter("sortNameAsc") != null) {
            sortValuesInTable(DBQueries.GET_SORTED_DATA_BY_NAME_FROM_TABLE_ASC);
        }
        if (req.getParameter("sortNameDesc") != null) {
            sortValuesInTable(DBQueries.GET_SORTED_DATA_BY_NAME_FROM_TABLE_DESC);
        }
        if (req.getParameter("sortLastNameAsc") != null) {
            sortValuesInTable(DBQueries.GET_SORTED_DATA_BY_LASTNAME_FROM_TABLE_ASC);
        }
        if (req.getParameter("sortLastNameDesc") != null) {
            sortValuesInTable(DBQueries.GET_SORTED_DATA_BY_LASTNAME_FROM_TABLE_DESC);
        }
        if (req.getParameter("uniteRows") != null) {
            uniteRows();
        }
        if (req.getParameter("setLanguage") != null) {
            req.setAttribute("langVariable", req.getParameter("language"));
            changeLocalizationSettings(req.getParameter("language"));
        }

        doGet(req, resp);
    }

    private void sortValuesInTable(String query) {
        controller.setFullContactDataList(controller.executeSortingDBQuery(query));
    }

    private void uniteRows() {
        //TODO
        UnitingService.uniteRows();
    }

    private void changeLocalizationSettings(String language) {
        controller.setResourceBundles(language);
    }

    private void addValueToList(HttpServletRequest req) {
        AddingValueToDBService.addValueToList(req, controller);
    }
}
