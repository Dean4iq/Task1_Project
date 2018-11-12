package ua.training.service;

import ua.training.model.*;
import ua.training.util.DBQueries;

import javax.servlet.http.HttpServletRequest;

class AddingValueToDBService {
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
                query.append(" VALUES ").append(queryValues);

                controller.addContactToList(query.toString());
            } else {
                CheckingRegExService.getWarnedRegexStrings(fullContactData, controller);
            }
        }
    }
}
