package models.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Resource {

    private String name;
    private Model model;
    private int section;
    private Map<String, Object> data;
    private List<Occurrence> occurrences;

    public Resource(String name, Model model, int section, Map<String, Object> data) {
        this.name = name;
        this.model = model;
        this.section = section;
        this.data = data;
        this.occurrences = new ArrayList<>();
    }

    public void addOccurrence(Occurrence occurrence) {
        this.occurrences.add(occurrence);
    }

    public String getName() {
        return data.get("Nome").toString();
    }

    public Model getModel() {
        return this.model;
    }

    public int getSection() { return Integer.parseInt(data.get("Setor").toString()); }

    public Map<String, Object> getData() {
        return this.data;
    }

    public List<Occurrence> getOccurrences() {
        return this.occurrences;
    }

}
