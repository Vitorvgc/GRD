package models.resource;

import java.util.Date;

public class Ocurrence {

    private OcurrenceType type;
    private Date date;
    private String details;

    public Ocurrence(OcurrenceType type, Date date, String details) {
        this.type = type;
        this.date = date;
        this.details = details;
    }

    public OcurrenceType getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public String getDetails() {
        return details;
    }
}
