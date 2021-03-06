package ua.training.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.util.DBQueries;
import ua.training.util.LocalizationLinks;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ContactFormServlet extends HttpServlet {
    private final Controller controller = new Controller();
    private final Map<String, Handler> commandMap = new HashMap<>();

    private final Logger log = LogManager.getLogger(ContactFormServlet.class);

    @Override
    public void init() throws ServletException {
        commandMap.put("addValue", object -> addValueToList((HttpServletRequest) object));
        commandMap.put("sortNameAsc",
                object -> sortValuesInTable(DBQueries.GET_SORTED_DATA_BY_NAME_FROM_TABLE_ASC));
        commandMap.put("sortNameDesc",
                object -> sortValuesInTable(DBQueries.GET_SORTED_DATA_BY_NAME_FROM_TABLE_DESC));
        commandMap.put("sortLastNameAsc",
                object -> sortValuesInTable(DBQueries.GET_SORTED_DATA_BY_LASTNAME_FROM_TABLE_ASC));
        commandMap.put("sortLastNameDesc",
                object -> sortValuesInTable(DBQueries.GET_SORTED_DATA_BY_LASTNAME_FROM_TABLE_DESC));
        commandMap.put("uniteRows",
                object -> uniteRows((HttpServletRequest) object));
        commandMap.put("deleteRows",
                object -> deleteRows((HttpServletRequest) object));
        commandMap.put("setLanguage", object -> {
            ((HttpServletRequest) object)
                    .setAttribute("langVariable",
                            ((HttpServletRequest) object).getParameter("language"));
            changeLocalizationSettings(((HttpServletRequest) object)
                    .getParameter("language"));

        });
        commandMap.put("searchInTable", object -> searchInTable((HttpServletRequest) object));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
        } catch (Exception ex) {
            log.error(ex.getStackTrace());
        }

        //table values
        request.setAttribute("contacts", controller.getFullContactDataList());
        request.setAttribute("tableDescription",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.TABLE_DESCRIPTION.getLocaleSource()));
        request.setAttribute("nameColumn",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.NAME_COLUMN.getLocaleSource()));
        request.setAttribute("lastNameColumn",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.LASTNAME_COLUMN.getLocaleSource()));
        request.setAttribute("nicknameColumn",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.NICKNAME_COLUMN.getLocaleSource()));
        request.setAttribute("phoneColumn",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.PHONE_COLUMN.getLocaleSource()));
        request.setAttribute("idColumn",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.ID_COLUMN.getLocaleSource()));

        //headers values
        request.setAttribute("inputDeclaration",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.INPUT_DECLARATION.getLocaleSource()));
        request.setAttribute("searchDeclaration",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.SEARCH_DECLARATION.getLocaleSource()));

        //buttons values
        request.setAttribute("buttonUnite",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.BUTTON_UNITE.getLocaleSource()));
        request.setAttribute("buttonDelete",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.BUTTON_DELETE.getLocaleSource()));
        request.setAttribute("buttonAdd",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.BUTTON_ADD.getLocaleSource()));
        request.setAttribute("buttonSearch",
                Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.BUTTON_SEARCH.getLocaleSource()));

        //other values
        request.setAttribute("langVariable", controller.getMessageResourceBundle().getLocale().toLanguageTag());
        request.setAttribute("FAQContent", buildFAQContent());
        request.setAttribute("regexStrings",
                CheckingRegExService.getRegexStrings(controller));

        try {
            request.getRequestDispatcher("/view/index.jsp").forward(request, response);
        } catch (Exception ex) {
            log.error(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        commandMap.entrySet().stream()
                .filter(elem -> request.getParameter(elem.getKey()) != null)
                .findFirst().get().getValue().handle(request);

        try {
            doGet(request, response);
        } catch (Exception ex) {
            log.error(Arrays.toString(ex.getStackTrace()));
        }
    }

    private String buildFAQContent() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<p>")
                .append(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.FAQ_SORT_INFO.getLocaleSource())).append("</p>")
                .append("<p>")
                .append(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.FAQ_INPUT_INFO.getLocaleSource())).append("</p>")
                .append("<p>")
                .append(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.FAQ_DELETE_INFO.getLocaleSource())).append("</p>")
                .append("<p>")
                .append(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.FAQ_UNITE_INFO.getLocaleSource())).append("</p>")
                .append("<p>")
                .append(Controller.getStringFromBundle(controller.getMessageResourceBundle(),
                        LocalizationLinks.FAQ_SEARCH_INFO.getLocaleSource())).append("</p>");
        return stringBuilder.toString();
    }

    private void sortValuesInTable(String query) {
        controller.setFullContactDataList(controller.executeSelectDBQuery(query));
    }

    private void searchInTable(HttpServletRequest request) {
        SearchingService
                .searchInTable(request.getParameter("searchingRequest"), controller);
    }

    private void uniteRows(HttpServletRequest request) {
        UnitingService.uniteRows(request, controller);
    }

    private void deleteRows(HttpServletRequest request) {
        DeletingRowsService.deleteRows(request, controller);
    }

    private void changeLocalizationSettings(String language) {
        controller.setResourceBundles(language);
    }

    private void addValueToList(HttpServletRequest req) {
        AddingValueToDBService.addRequestValueToDB(req, controller);
    }

    interface Handler {
        void handle(Object object);
    }
}


