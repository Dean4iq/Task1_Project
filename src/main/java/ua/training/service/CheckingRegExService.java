package ua.training.service;

import ua.training.util.RegExLinks;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

class CheckingRegExService {
    private CheckingRegExService() {
    }

    static Map<String, String> getRegexStrings(Controller controller) {
        Map<String, String> regexMap = new HashMap<>();
        regexMap.put("regexName",
                Controller.getStringFromBundle(controller.getRegexResourceBundle(),
                        RegExLinks.NAME_REGEX.getRegExSource()));
        regexMap.put("regexLastName",
                Controller.getStringFromBundle(controller.getRegexResourceBundle(),
                        RegExLinks.LAST_NAME_REGEX.getRegExSource()));
        regexMap.put("regexNickname",
                Controller.getStringFromBundle(controller.getRegexResourceBundle(),
                        RegExLinks.NICKNAME_REGEX.getRegExSource()));
        regexMap.put("regexPhone",
                Controller.getStringFromBundle(controller.getRegexResourceBundle(),
                        RegExLinks.PHONE_REGEX.getRegExSource()));
        regexMap.put("regexId",
                Controller.getStringFromBundle(controller.getRegexResourceBundle(),
                        RegExLinks.ID_REGEX.getRegExSource()));

        return regexMap;
    }

    static boolean checkRegexString(String stringToCheck,
                                    String checkedField, Controller controller) {
        return stringToCheck
                .matches(Controller.getStringFromBundle(controller.getRegexResourceBundle(),
                        RegExLinks.valueOf(checkedField).getRegExSource()));

    }
}
