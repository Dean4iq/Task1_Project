package ua.training.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.*;
import ua.training.util.DBQueries;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class UnitingService {
    private static final Logger log = LogManager.getLogger(UnitingService.class);
    private static Map<String, GetterHandler> checkNullsHandler;
    private static String SQL_HEADER = "='";
    private static String SQL_ENDING = "',";
    private static FullContactData fullContactData;

    static {
        checkNullsHandler = new HashMap<>();

        checkNullsHandler.put("name", () -> fullContactData.getName());
        checkNullsHandler.put("lastname", () -> fullContactData.getLastName());
        checkNullsHandler.put("nickname", () -> fullContactData.getNickname());
        checkNullsHandler.put("phone", () -> fullContactData.getPhone());
        checkNullsHandler.put("id", () -> fullContactData.getId());
    }

    private static void setFullContactData(FullContactData fullContactData) {
        UnitingService.fullContactData = fullContactData;
    }

    private UnitingService() {
    }

    static void uniteRows(HttpServletRequest request, Controller controller) {
        StringBuilder stringBuilder = new StringBuilder()
                .append(DBQueries.HEADER_SELECT_FROM_WHERE_ID);

        if (request.getParameterValues("selectedRows").length == 2) {
            Arrays.stream(request.getParameterValues("selectedRows"))
                    .forEach(elem -> stringBuilder.append(elem).append(","));
            stringBuilder.deleteCharAt(stringBuilder.length() - 1).append(")");

            List<FullContactData> fullContactDataList =
                    controller.executeSelectDBQuery(stringBuilder.toString());

            checkContactsFields(fullContactDataList.get(0),
                    fullContactDataList.get(1));

            controller.executeUpdateDBQuery(new StringBuilder()
                    .append(buildUpdateRequest(fullContactDataList.get(0)))
                    .toString());
            controller.executeUpdateDBQuery(new StringBuilder()
                    .append(buildUpdateRequest(fullContactDataList.get(1)))
                    .toString());
        }
    }

    private static String buildUpdateRequest(FullContactData fullContactData) {
        StringBuilder stringBuilder = new StringBuilder()
                .append(DBQueries.HEADER_UPDATE_ALL_COLUMNS);

        setFullContactData(fullContactData);

        checkNullsHandler.entrySet().stream()
                .filter(elem -> elem.getValue().handleGet() != null)
                .forEach(elem -> stringBuilder
                        .append(elem.getKey())
                        .append(SQL_HEADER)
                        .append(((ContactData) elem.getValue().handleGet()).getContactDataField())
                        .append(SQL_ENDING));

        stringBuilder.deleteCharAt(stringBuilder.length() - 1)
                .append(" where db_id = ").append(fullContactData.getRowId())
                .append(";");

        return stringBuilder.toString();
    }

    private static void checkContactsFields(FullContactData fullContactDataFirst,
                                            FullContactData fullContactDataSecond) {
        try {
            compareContactData(fullContactDataFirst,
                    fullContactDataSecond,
                    "getName", "setName");

            compareContactData(fullContactDataFirst,
                    fullContactDataSecond,
                    "getLastName", "setLastName");

            compareContactData(fullContactDataFirst,
                    fullContactDataSecond,
                    "getNickname", "setNickname");

            compareContactData(fullContactDataFirst,
                    fullContactDataSecond,
                    "getPhone", "setPhone");

            compareContactData(fullContactDataFirst,
                    fullContactDataSecond,
                    "getId", "setId");
        } catch (Exception ex) {
            log.error(Arrays.toString(ex.getStackTrace()));
        }
    }

    private static void compareContactData(FullContactData contactDataFirst,
                                           FullContactData contactDataSecond,
                                           String getterMethod,
                                           String setterMethod)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (contactDataFirst.getClass().getMethod(getterMethod)
                .invoke(contactDataFirst) == null
                && contactDataSecond.getClass().getMethod(getterMethod)
                .invoke(contactDataSecond) != null) {
            contactDataFirst.getClass()
                    .getMethod(setterMethod,
                            contactDataSecond.getClass().getMethod(getterMethod)
                                    .invoke(contactDataSecond).getClass())
                    .invoke(contactDataFirst, contactDataSecond.getClass()
                            .getMethod(getterMethod).invoke(contactDataSecond));
        } else if (contactDataFirst.getClass().getMethod(getterMethod)
                .invoke(contactDataFirst) != null
                && contactDataSecond.getClass().getMethod(getterMethod)
                .invoke(contactDataSecond) == null) {
            contactDataSecond.getClass()
                    .getMethod(setterMethod,
                            contactDataFirst.getClass().getMethod(getterMethod)
                                    .invoke(contactDataFirst).getClass())
                    .invoke(contactDataSecond, contactDataFirst.getClass()
                            .getMethod(getterMethod).invoke(contactDataFirst));
        }
    }

    interface GetterHandler {
        Object handleGet();
    }
}
