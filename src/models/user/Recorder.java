package models.user;

public class Recorder extends User {

    public Recorder(String name, int section) {
        super(name, section);
    }

    public String getFunction() {
        return "Recorder";
    }
}
