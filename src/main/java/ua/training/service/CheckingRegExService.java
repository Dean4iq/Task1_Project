package ua.training.service;

import ua.training.model.ContactData;
import ua.training.model.FullContactData;
import ua.training.util.RegExLinks;

import java.util.*;

class CheckingRegExService {
    private static boolean warnedName = false;
    private static boolean warnedLastName = false;
    private static boolean warnedNickname = false;
    private static boolean warnedPhone = false;
    private static boolean warnedId = false;

    private CheckingRegExService() {
    }

    static Map<String, String> getRegexStrings(Controller controller) {
        Map<String, String> regexMap = new HashMap<>();

        regexMap.put("regexName",
                getCheckedWarningRegexString(warnedName,
                        Controller.getStringFromBundle(controller.getRegexResourceBundle(),
                                RegExLinks.NAME_REGEX.getRegExSource())));
        regexMap.put("regexLastName",
                getCheckedWarningRegexString(warnedLastName,
                        Controller.getStringFromBundle(controller.getRegexResourceBundle(),
                                RegExLinks.LAST_NAME_REGEX.getRegExSource())));
        regexMap.put("regexNickname",
                getCheckedWarningRegexString(warnedNickname,
                        Controller.getStringFromBundle(controller.getRegexResourceBundle(),
                                RegExLinks.NICKNAME_REGEX.getRegExSource())));
        regexMap.put("regexPhone",
                getCheckedWarningRegexString(warnedPhone,
                        Controller.getStringFromBundle(controller.getRegexResourceBundle(),
                                RegExLinks.PHONE_REGEX.getRegExSource())));
        regexMap.put("regexId",
                getCheckedWarningRegexString(warnedId,
                        Controller.getStringFromBundle(controller.getRegexResourceBundle(),
                                RegExLinks.ID_REGEX.getRegExSource())));

        warnedName = false;
        warnedLastName = false;
        warnedNickname = false;
        warnedPhone = false;
        warnedId = false;

        return regexMap;
    }

    static boolean checkRegexFullContactData(FullContactData fullContactData,
                                             Controller controller) {
        Map<String, String> contactDatalist = new HashMap<>();

        if (fullContactData.getName() != null) {
            contactDatalist.put(RegExLinks.NAME_REGEX.getRegExSource(),
                    fullContactData.getName().getContactDataField());
        }
        if (fullContactData.getLastName() != null) {
            contactDatalist.put(RegExLinks.LAST_NAME_REGEX.getRegExSource(),
                    fullContactData.getLastName().getContactDataField());
        }
        if (fullContactData.getNickname() != null) {
            contactDatalist.put(RegExLinks.NICKNAME_REGEX.getRegExSource(),
                    fullContactData.getNickname().getContactDataField());
        }
        if (fullContactData.getPhone() != null) {
            contactDatalist.put(RegExLinks.PHONE_REGEX.getRegExSource(),
                    fullContactData.getPhone().getContactDataField());
        }
        if (fullContactData.getId() != null) {
            contactDatalist.put(RegExLinks.ID_REGEX.getRegExSource(),
                    fullContactData.getId().getContactDataField());
        }

        return contactDatalist.entrySet().stream()
                .anyMatch(elem ->
                        !(elem.getValue().matches(Controller
                                .getStringFromBundle(controller.getRegexResourceBundle(),
                                        elem.getKey()))));
    }

    private static boolean checkRegexContactDataString(ContactData contactData,
                                               String regEx,
                                               Controller controller) {
        return contactData.getContactDataField().matches(Controller
                .getStringFromBundle(controller.getRegexResourceBundle(), regEx));
    }

    private static String getCheckedWarningRegexString(boolean condition, String message) {
        if (condition) {
            return new StringBuilder().append("<p style=\"background:red;\">")
                    .append(message).append("</p>").toString();
        }
        return message;
    }

    static void getWarnedRegexStrings(FullContactData fullContactData,
                                      Controller controller) {
        if (fullContactData.getName() != null && !checkRegexContactDataString(fullContactData.getName(),
                RegExLinks.NAME_REGEX.getRegExSource(), controller)) {
            warnedName = true;
        }
        if (fullContactData.getLastName() != null && !checkRegexContactDataString(fullContactData.getLastName(),
                RegExLinks.LAST_NAME_REGEX.getRegExSource(), controller)) {
            warnedLastName = true;
        }
        if (fullContactData.getNickname() != null && !checkRegexContactDataString(fullContactData.getNickname(),
                RegExLinks.NICKNAME_REGEX.getRegExSource(), controller)) {
            warnedNickname = true;
        }
        if (fullContactData.getPhone() != null && !checkRegexContactDataString(fullContactData.getPhone(),
                RegExLinks.PHONE_REGEX.getRegExSource(), controller)) {
            warnedPhone = true;
        }
        if (fullContactData.getId() != null && !checkRegexContactDataString(fullContactData.getId(),
                RegExLinks.ID_REGEX.getRegExSource(), controller)) {
            warnedId = true;
        }
    }
}
