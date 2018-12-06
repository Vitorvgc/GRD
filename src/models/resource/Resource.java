package models.resource;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Resource {

    private String name;
    private String section;
    private Model model;
    private List<Pair<String, Object>> data;
    private List<Occurrence> occurrences;

    public Resource(Model model, List<Pair<String, Object>> data) {

        Optional nameField = data.stream().filter(field -> field.getKey().equals("nome")).findFirst();
        Optional sectionField = data.stream().filter(field -> field.getKey().equals("setor")).findFirst();

        if (!nameField.isPresent() || !sectionField.isPresent())
            throw new RuntimeException();

        this.name = nameField.get().toString();
        this.section = sectionField.get().toString();
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

    public List<Pair<String, Object>> getData() {
        return this.data;
    }

    public List<Occurrence> getOccurrences() {
        return this.occurrences;
    }

}
