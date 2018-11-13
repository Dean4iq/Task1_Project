package ua.training.service;

import ua.training.util.DBQueries;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

class DeletingRowsService {
    private DeletingRowsService() {
    }

    static void deleteRows(HttpServletRequest request, Controller controller) {

        StringBuilder stringBuilder = new StringBuilder().append(DBQueries.DELETE_BY_ID_QUERY);
        Arrays.stream(request.getParameterValues("selectedRows"))
                .forEach(elem -> stringBuilder.append(elem).append(","));
        stringBuilder.deleteCharAt(stringBuilder.length() - 1).append(")");
        controller.executeDeleteQuery(stringBuilder.toString());
    }
}
