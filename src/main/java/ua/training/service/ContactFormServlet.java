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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        request.setAttribute("langVariable", controller.getMessageResourceBundle().getLocale().toLanguageTag());
        request.setAttribute("contacts", controller.getFullContactDataList());
        request.setAttribute("tableDescription",
                new String(StandardCharsets.ISO_8859_1.encode(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.TABLE_DESCRIPTION.getLocaleSource())).array()));
        request.setAttribute("nameColumn",
                new String(StandardCharsets.ISO_8859_1.encode(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.NAME_COLUMN.getLocaleSource())).array()));
        request.setAttribute("lastNameColumn",
                new String(StandardCharsets.ISO_8859_1.encode(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.LASTNAME_COLUMN.getLocaleSource())).array()));
        request.setAttribute("nicknameColumn",
                new String(StandardCharsets.ISO_8859_1.encode(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.NICKNAME_COLUMN.getLocaleSource())).array()));
        request.setAttribute("phoneColumn",
                new String(StandardCharsets.ISO_8859_1.encode(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.PHONE_COLUMN.getLocaleSource())).array()));
        request.setAttribute("idColumn",
                new String(StandardCharsets.ISO_8859_1.encode(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.ID_COLUMN.getLocaleSource())).array()));
        request.setAttribute("inputDeclaration",
                new String(StandardCharsets.ISO_8859_1.encode(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.INPUT_DECLARATION.getLocaleSource())).array()));

        request.getRequestDispatcher("/view/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        if (request.getParameter("addValue") != null) {
            addValueToList(request);
        }
        if (request.getParameter("sortNameAsc") != null) {
            sortValuesInTable(DBQueries.GET_SORTED_DATA_BY_NAME_FROM_TABLE_ASC);
        }
        if (request.getParameter("sortNameDesc") != null) {
            sortValuesInTable(DBQueries.GET_SORTED_DATA_BY_NAME_FROM_TABLE_DESC);
        }
        if (request.getParameter("sortLastNameAsc") != null) {
            sortValuesInTable(DBQueries.GET_SORTED_DATA_BY_LASTNAME_FROM_TABLE_ASC);
        }
        if (request.getParameter("sortLastNameDesc") != null) {
            sortValuesInTable(DBQueries.GET_SORTED_DATA_BY_LASTNAME_FROM_TABLE_DESC);
        }
        if (request.getParameter("uniteRows") != null) {
            uniteRows(request);
        }
        if (request.getParameter("deleteRows") != null) {
            deleteRows(request);
        }
        if (request.getParameter("setLanguage") != null) {
            request.setAttribute("langVariable", request.getParameter("language"));
            changeLocalizationSettings(request.getParameter("language"));
        }

        doGet(request, response);
    }

    private void sortValuesInTable(String query) {
        controller.setFullContactDataList(controller.executeSortingDBQuery(query));
    }

    private void uniteRows(HttpServletRequest request) {
        //TODO
        UnitingService.uniteRows(request, controller);
    }

    private void deleteRows(HttpServletRequest request) {
        //TODO
        DeletingRowsService.deleteRows(request, controller);
    }

    private void changeLocalizationSettings(String language) {
        controller.setResourceBundles(language);
    }

    private void addValueToList(HttpServletRequest req) {
        AddingValueToDBService.addValueToList(req, controller);
    }
}
