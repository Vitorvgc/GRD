package models.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Resource {

    private String name;
    private Model model;
    private int section;
    private Map<String, Object> data;
    private List<OcurrenceType> ocurrences;

    public Resource(String name, Model model, int section, Map<String, Object> data) {
        this.name = name;
        this.model = model;
        this.section = section;
        this.data = data;
        this.ocurrences = new ArrayList<>();
    }

    public void addOcurrence(OcurrenceType ocurrence) {
        this.ocurrences.add(ocurrence);
    }

    public String getName() {
        return this.name;
    }

    public Model getModel() {
        return this.model;
    }

    public int getSection() {
        return this.section;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public List<OcurrenceType> getOcurrences() {
        return this.ocurrences;
    }

}
