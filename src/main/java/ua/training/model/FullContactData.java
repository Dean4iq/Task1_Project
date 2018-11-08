package ua.training.model;

public class FullContactData {
    private NameContact name;
    private LastNameContact lastName;
    private NicknameContact nickname;
    private PhoneContact phone;
    private IdContact id;

    public FullContactData(){}

    public FullContactData(NameContact name, LastNameContact lastName,
                           NicknameContact nickname, PhoneContact phone,
                           IdContact id) {
        this.name = name;
        this.lastName = lastName;
        this.nickname = nickname;
        this.phone = phone;
        this.id = id;
    }

    public NameContact getName() {
        return name;
    }

    public void setName(NameContact name) {
        this.name = name;
    }

    public LastNameContact getLastName() {
        return lastName;
    }

    public void setLastName(LastNameContact lastName) {
        this.lastName = lastName;
    }

    public NicknameContact getNickname() {
        return nickname;
    }

    public void setNickname(NicknameContact nickname) {
        this.nickname = nickname;
    }

    public PhoneContact getPhone() {
        return phone;
    }

    public void setPhone(PhoneContact phone) {
        this.phone = phone;
    }

    public IdContact getId() {
        return id;
    }

    public void setId(IdContact id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FullContactData{" +
                "name=" + name +
                ", lastName=" + lastName +
                ", nickname=" + nickname +
                ", phone=" + phone +
                ", id=" + id +
                '}';
    }
}
