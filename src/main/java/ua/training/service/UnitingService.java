package ua.training.service;

import ua.training.model.ContactData;
import ua.training.model.FullContactData;
import ua.training.util.DBQueries;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

class UnitingService {
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
                    controller.executeAnyDBQuery(stringBuilder.toString());

            checkContactsFields(fullContactDataList.get(0),
                    fullContactDataList.get(1));

            controller.executeAnyDBQuery(new StringBuilder()
                    .append(buildUpdateRequest(fullContactDataList.get(0)))
                    .append(buildUpdateRequest(fullContactDataList.get(1)))
                    .toString());
        }
    }

    private static String buildUpdateRequest(FullContactData fullContactData) {
        StringBuilder stringBuilder = new StringBuilder()
                .append(DBQueries.HEADER_UPDATE_ALL_COLUMNS);

        if (fullContactData.getName() != null) {
            stringBuilder.append("name = ")
                    .append(fullContactData.getName().getContactData())
            .append(", ");
        }
        if (fullContactData.getLastName() != null) {
            stringBuilder.append("lastname = ")
                    .append(fullContactData.getLastName().getContactData())
                    .append(", ");
        }
        if (fullContactData.getNickname() != null) {
            stringBuilder.append("nickname = ")
                    .append(fullContactData.getNickname().getContactData())
                    .append(", ");
        }
        if (fullContactData.getPhone() != null) {
            stringBuilder.append("phone = ")
                    .append(fullContactData.getPhone().getContactData())
                    .append(", ");
        }
        if (fullContactData.getId() != null) {
            stringBuilder.append("id = ")
                    .append(fullContactData.getId().getContactData())
                    .append(", ");
        }

        stringBuilder.deleteCharAt(stringBuilder.length()-1)
                .append(" WHERE db_id = ").append(fullContactData.getRowId());

        return stringBuilder.toString();
    }

    private static void checkContactsFields(FullContactData fullContactDataFirst,
                                            FullContactData fullContactDataSecond) {
        compareContactData(fullContactDataFirst.getName(),
                fullContactDataSecond.getName());
        compareContactData(fullContactDataFirst.getLastName(),
                fullContactDataSecond.getLastName());
        compareContactData(fullContactDataFirst.getNickname(),
                fullContactDataSecond.getNickname());
        compareContactData(fullContactDataFirst.getPhone(),
                fullContactDataSecond.getPhone());
        compareContactData(fullContactDataFirst.getId(),
                fullContactDataSecond.getId());
    }

    private static void compareContactData(ContactData contactDataFirst,
                                           ContactData contactDataSecond) {
        if (contactDataFirst == null && contactDataSecond != null) {
            contactDataFirst = contactDataSecond;
        } else if (contactDataFirst != null && contactDataSecond == null) {
            contactDataSecond = contactDataFirst;
        }
    }
}
