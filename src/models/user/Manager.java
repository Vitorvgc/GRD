package models.user;

public class Manager extends User {

    public Manager(String name, int section) {
        super(name, section);
    }

    public String getFunction() {
        return "Manager";
    }
}
