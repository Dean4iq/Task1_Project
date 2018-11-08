package ua.training.model;

public abstract class ContactData implements Comparable<ContactData> {
    String contactData;

    ContactData(String contactData) {
        this.contactData = contactData;
    }

    public void setData(String contactData) {
        this.contactData = contactData;
    }

    public String getContactData() {
        return contactData;
    }

    @Override
    public int compareTo(ContactData o) {
        return this.getContactData().compareTo(o.getContactData());
    }

    @Override
    public String toString() {
        return contactData;
    }
}
