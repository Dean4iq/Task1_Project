package ua.training.util;

public enum LocalizationLinks {
    SOURCE("messages"),
    TABLE_DESCRIPTION("table.description"),
    NAME_COLUMN("output.column.name"),
    LASTNAME_COLUMN("output.column.lastname"),
    NICKNAME_COLUMN("output.column.nickname"),
    PHONE_COLUMN("output.column.phone"),
    ID_COLUMN("output.column.id"),
    INPUT_DECLARATION("input.msg"),
    BUTTON_UNITE("button.msg.unite"),
    BUTTON_DELETE("button.msg.delete"),
    BUTTON_ADD("button.msg.add"),
    FAQ_SORT_INFO("faq.info.sort"),
    FAQ_INPUT_INFO("faq.info.input"),
    FAQ_DELETE_INFO("faq.info.delete"),
    FAQ_UNITE_INFO("faq.info.unite");

    String localeSource;

    LocalizationLinks(String localeSource){
        this.localeSource = localeSource;
    }

    public String getLocaleSource() {
        return localeSource;
    }
}
