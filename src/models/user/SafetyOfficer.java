package models.user;

public class SafetyOfficer extends User {

    public SafetyOfficer(String name, int section) {
        super(name, section);
    }

    public String getFunction() {
        return "Safety Officer";
    }
}
