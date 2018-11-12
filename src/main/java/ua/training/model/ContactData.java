package ua.training.model;

public abstract class ContactData implements Comparable<ContactData> {
    private String contactDataField;

    ContactData(String contactDataField) {
        this.contactDataField = contactDataField;
    }

    public void setData(String contactData) {
        this.contactDataField = contactData;
    }

    public String getContactDataField() {
        return contactDataField;
    }

    @Override
    public int compareTo(ContactData o) {
        return this.getContactDataField().compareTo(o.getContactDataField());
    }

    @Override
    public String toString() {
        return contactDataField;
    }
}
