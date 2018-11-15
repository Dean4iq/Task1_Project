package ua.training.service;

import ua.training.model.*;
import ua.training.util.DBQueries;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

class AddingValueToDBService {
    private static Map<String, Handler> handlerMap = new HashMap<>();

    private AddingValueToDBService() {
    }

    static void addRequestValueToDB(HttpServletRequest request,
                                    Controller controller) {
        final String name = request.getParameter("name");
        final String lastName = request.getParameter("lastname");
        final String nickname = request.getParameter("nickname");
        final String phone = request.getParameter("phone");
        final String id = request.getParameter("id");


        if (!name.equals("") || !lastName.equals("") || !nickname.equals("")
                || !phone.equals("") || !id.equals("")) {
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

            if (!CheckingRegExService.checkRegexFullContactData(fullContactData, controller)) {
                StringBuilder query = new StringBuilder().append(DBQueries.HEADER_INSERT_TO_DB);
                StringBuilder queryValues = new StringBuilder().append("(");

                handlerMap.put("name", fullContactData::getName);
                handlerMap.put("lastname", fullContactData::getLastName);
                handlerMap.put("nickname", fullContactData::getNickname);
                handlerMap.put("phone", fullContactData::getPhone);
                handlerMap.put("id", fullContactData::getId);

                handlerMap.entrySet().stream()
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

                System.out.println(query.toString());
                controller.addContactToList(query.toString());
            } else {
                CheckingRegExService.getWarnedRegexStrings(fullContactData, controller);
            }
        }
    }

    interface Handler {
        Object getterHandler();
    }
}
