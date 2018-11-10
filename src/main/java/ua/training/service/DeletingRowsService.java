package ua.training.service;

import ua.training.util.DBQueries;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

class DeletingRowsService {
    private DeletingRowsService(){}

    static void deleteRows(HttpServletRequest request, Controller controller){
        String[] selectedRowsId = request.getParameterValues("selectedRows");

        StringBuilder stringBuilder = new StringBuilder().append(DBQueries.DELETE_BY_ID_QUERY);
        Arrays.stream(selectedRowsId).forEach(elem -> stringBuilder.append(elem).append(","));
        stringBuilder.deleteCharAt(stringBuilder.length()-1).append(")");
        controller.getDbConnector().executeQuery(stringBuilder.toString());
        controller.getDataFromDatabase();
    }
}
