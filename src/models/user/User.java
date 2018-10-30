package models.user;

public abstract class User {

    private String name;
    private int section;

    public User(String name, int section) {
        this.name = name;
        this.section = section;
    }

    public String getName() {
        return this.name;
    }

    public int getSection() {
        return this.section;
    }

    abstract String getFunction();
}
