package models.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Resource {

    private String name;
    private String section;
    private Model model;
    private Map<String, Object> data;
    private List<Occurrence> occurrences;

    public Resource(Model model, Map<String, Object> data) {
        this.name = data.get("Nome").toString();
        this.section = data.get("Setor").toString();
        this.model = model;
        this.data = data;
        this.occurrences = new ArrayList<>();
    }

    public void addOccurrence(Occurrence occurrence) {
        this.occurrences.add(occurrence);
    }

    public String getName() {
        return this.name;
    }

    public Model getModel() {
        return this.model;
    }

    public String getSection() { return this.section; }

    public Map<String, Object> getData() {
        return this.data;
    }

    public List<Occurrence> getOccurrences() {
        return this.occurrences;
    }

}
