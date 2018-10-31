package models.resource;

import java.util.Date;

public class Occurrence {

    private OccurrenceType type;
    private Date date;
    private String details;

    public Occurrence(OccurrenceType type, Date date, String details) {
        this.type = type;
        this.date = date;
        this.details = details;
    }

    public OccurrenceType getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public String getDetails() {
        return details;
    }
}
