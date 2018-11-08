package ua.training.service;

import ua.training.model.*;
import ua.training.util.DBQueries;
import ua.training.util.LocalizationLinks;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        //req.setAttribute("contacts", controller.getFullContactDataList());
        req.setAttribute("greeting",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.GREETING_MESSAGE.getLocaleSource()));
        req.setAttribute("tableDescription",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.TABLE_DESCRIPTION.getLocaleSource()));
        req.setAttribute("nameColumn",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.NAME_COLUMN.getLocaleSource()));
        req.setAttribute("lastNameColumn",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.LASTNAME_COLUMN.getLocaleSource()));
        req.setAttribute("nicknameColumn",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.NICKNAME_COLUMN.getLocaleSource()));
        req.setAttribute("phoneColumn",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.PHONE_COLUMN.getLocaleSource()));
        req.setAttribute("idColumn",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.ID_COLUMN.getLocaleSource()));
        req.setAttribute("inputDeclaration",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.INPUT_DECLARATION.getLocaleSource()));

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

        doGet(req, resp);
    }

    private void sortValuesInTable(String query) {
        controller.setFullContactDataList(controller.executeDBQuery(query));
    }

    private void uniteRows() {
        //TODO
    }

    private void addValueToList(HttpServletRequest req) {
        final String name = req.getParameter("name");
        final String lastName = req.getParameter("lastname");
        final String nickname = req.getParameter("nickname");
        final String phone = req.getParameter("phone");
        final String id = req.getParameter("id");

        if (!name.equals("") || !lastName.equals("") || !nickname.equals("") ||
                !phone.equals("") || !id.equals("")) {
            final FullContactData fullContactData = new FullContactData();

            if (!name.equals("")) {
                fullContactData.setName(new NameContact(name));
            }
            if (!lastName.equals("")) {
                fullContactData.setLastName(new LastNameContact(lastName));
            }
            if (!nickname.equals("")) {
                fullContactData.setNickname(new NicknameContact(nickname));
            }
            if (!phone.equals("")) {
                fullContactData.setPhone(new PhoneContact(phone));
            }
            if (!id.equals("")) {
                fullContactData.setId(new IdContact(id));
            }

            controller.addContactToList(fullContactData);
        }
    }
}
