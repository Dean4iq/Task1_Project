package ua.training.service;

import ua.training.model.FullContactData;

import java.util.List;

public class SortingService {
    static void sortFullContactDataListByName(List<FullContactData> listToSort, String sortingType) {
        listToSort.sort((o1, o2) -> {
            if(sortingType.equals("asc")){
                return o1.getName().compareTo(o2.getName());
            } else {
                return o2.getName().compareTo(o1.getName());
            }
        });
    }

    static void sortFullContactDataListByLastName(List<FullContactData> listToSort, String sortingType) {
        listToSort.sort((o1, o2) -> {
            if(sortingType.equals("asc")){
                return o1.getLastName().compareTo(o2.getLastName());
            } else {
                return o2.getLastName().compareTo(o1.getLastName());
            }
        });
    }
}
