package ua.training.service;

import ua.training.model.*;
import ua.training.util.RegExLinks;

import javax.servlet.http.HttpServletRequest;

class AddingValueToDBService {
    private AddingValueToDBService() {
    }

    static void addValueToList(HttpServletRequest req, Controller controller) {
        final String name = req.getParameter("name");
        final String lastName = req.getParameter("lastname");
        final String nickname = req.getParameter("nickname");
        final String phone = req.getParameter("phone");
        final String id = req.getParameter("id");

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
                System.out.println(fullContactData.getName().getContactData());
                controller.addContactToList(fullContactData);
            } else {
                CheckingRegExService.getWarnedRegexStrings(fullContactData, controller);
            }
        }
    }
}
