package ua.training.service;

import ua.training.model.FullContactData;

import java.util.List;
import java.util.stream.Collectors;

public class SearchingService {
    private SearchingService(){}

    static void searchInTable(String request, Controller controller){
        List<FullContactData> contactDataList = controller.getDataFromDatabase();

        controller.setFullContactDataList(contactDataList.stream().filter(elem ->
                elem.getRows().stream().anyMatch(arrayElem ->
                        arrayElem.contains(request))).collect(Collectors.toList()));
    }
}
