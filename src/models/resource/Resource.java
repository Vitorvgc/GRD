package models.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Resource {

    private String name;
    private Model model;
    private int section;
    private Map<String, Object> data;
    private List<Ocurrence> ocurrences;

    public Resource(String name, Model model, int section, Map<String, Object> data) {
        this.name = name;
        this.model = model;
        this.section = section;
        this.data = data;
        this.ocurrences = new ArrayList<>();
    }

    public void addOcurrence(Ocurrence ocurrence) {
        this.ocurrences.add(ocurrence);
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

    public List<Ocurrence> getOcurrences() {
        return this.ocurrences;
    }

}
