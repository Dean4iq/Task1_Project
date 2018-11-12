package ua.training.service;

import ua.training.model.*;

import javax.servlet.http.HttpServletRequest;

class AddingValueToDBService {
    private AddingValueToDBService() {
    }

    static void addValueToList(HttpServletRequest request, Controller controller) {
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
                controller.addContactToList(fullContactData);
            } else {
                CheckingRegExService.getWarnedRegexStrings(fullContactData, controller);
            }
        }
    }
}
