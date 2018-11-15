package ua.training.service;

import ua.training.model.*;
import ua.training.util.DBQueries;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

class AddingValueToDBService {
    private static Map<String, InitialHandler> initialHandlerMap = new HashMap<>();
    private static Map<String, ConditionalHandler> conditionalHandlerMap = new HashMap<>();

    private AddingValueToDBService() {
    }

    static void addRequestValueToDB(HttpServletRequest request,
                                    Controller controller) {
        final String name = request.getParameter("name");
        final String lastName = request.getParameter("lastname");
        final String nickname = request.getParameter("nickname");
        final String phone = request.getParameter("phone");
        final String id = request.getParameter("id");

        initialHandlerMap.put(name, (fullContactData, data) ->
                fullContactData.setName(new NameContact(data)));
        initialHandlerMap.put(lastName, (fullContactData, data) ->
                fullContactData.setLastName(new LastNameContact(data)));
        initialHandlerMap.put(nickname, (fullContactData, data) ->
                fullContactData.setNickname(new NicknameContact(data)));
        initialHandlerMap.put(phone, (fullContactData, data) ->
                fullContactData.setPhone(new PhoneContact(data)));
        initialHandlerMap.put(id, (fullContactData, data) ->
                fullContactData.setId(new IdContact(data)));

        if (initialHandlerMap.entrySet().stream()
                .anyMatch(elem -> !elem.getKey().equals(""))) {
            final FullContactData fullContactData = new FullContactData();

            initialHandlerMap.entrySet().stream()
                    .filter(elem -> !elem.getKey().equals(""))
                    .forEach(elem -> elem.getValue().handle(fullContactData, elem.getKey()));

            if (!CheckingRegExService.checkRegexFullContactData(fullContactData, controller)) {
                StringBuilder query = new StringBuilder().append(DBQueries.HEADER_INSERT_TO_DB);
                StringBuilder queryValues = new StringBuilder().append("(");

                conditionalHandlerMap.put("name", fullContactData::getName);
                conditionalHandlerMap.put("lastname", fullContactData::getLastName);
                conditionalHandlerMap.put("nickname", fullContactData::getNickname);
                conditionalHandlerMap.put("phone", fullContactData::getPhone);
                conditionalHandlerMap.put("id", fullContactData::getId);

                conditionalHandlerMap.entrySet().stream()
                        .filter(elem -> elem.getValue().getterHandler() != null)
                        .forEach(elem -> {
                            query.append(elem.getKey()).append(",");
                            queryValues.append("'")
                                    .append(elem.getValue().getterHandler())
                                    .append("',");
                        });

                queryValues.deleteCharAt(queryValues.length() - 1);
                query.deleteCharAt(query.length() - 1);
                query.append(")");
                queryValues.append(")");
                query.append(" VALUES ").append(queryValues);

                controller.addContactToList(query.toString());
            } else {
                CheckingRegExService.getWarnedRegexStrings(fullContactData, controller);
            }
        }
    }

    interface InitialHandler {
        void handle(FullContactData fullContactData, String data);
    }

    interface ConditionalHandler {
        Object getterHandler();
    }
}
