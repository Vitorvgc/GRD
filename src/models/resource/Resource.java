package models.resource;

import java.util.List;
import java.util.Map;

public class Resource {

    private Model model;
    private int section;
    private Map<String, Object> data;
    private List<Ocurrence> ocurrences;

    public Resource(Model model, int section, Map<String, Object> data) {
        this.model = model;
        this.section = section;
        this.data = data;
    }

    public void addOcurrence(Ocurrence ocurrence) {
        this.ocurrences.add(ocurrence);
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

    public List<Ocurrence> getOcurrences() {
        return this.ocurrences;
    }

}
