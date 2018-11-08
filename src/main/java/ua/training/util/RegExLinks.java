package ua.training.util;

public enum RegExLinks {
    SOURCE("regex"),
    PHONE_REGEX("regex.phone"),
    ID_REGEX("regex.id"),
    NAME_REGEX("regex.name"),
    LAST_NAME_REGEX("regex.lastname"),
    NICKNAME_REGEX("regex.nickname");

    String regExSource;

    RegExLinks(String regExSource){
        this.regExSource = regExSource;
    }

    public String getRegExSource() {
        return regExSource;
    }
}
