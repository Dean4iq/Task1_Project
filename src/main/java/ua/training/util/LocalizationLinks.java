package ua.training.util;

public enum LocalizationLinks {
    SOURCE("messages"),
    GREETING_MESSAGE("message.greeting"),
    TABLE_DESCRIPTION("table.description"),
    NAME_COLUMN("output.column.name"),
    LASTNAME_COLUMN("output.column.lastname"),
    NICKNAME_COLUMN("output.column.nickname"),
    PHONE_COLUMN("output.column.phone"),
    ID_COLUMN("output.column.id"),
    INPUT_DECLARATION("input.msg");

    String localeSource;

    LocalizationLinks(String localeSource){
        this.localeSource = localeSource;
    }

    public String getLocaleSource() {
        return localeSource;
    }
}
