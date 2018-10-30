package models.resource;

import java.util.List;
import java.util.Map;

public class Model {

    private String name;
    private Map<String, Class> parameters;
    private List<Ocurrence> ocurrences;

    Model(String name, Map<String, Class> parameters, List<Ocurrence> ocurrences) {
        this.name = name;
        this.parameters = parameters;
        this.ocurrences = ocurrences;
    }

    public void insertOcurrence(Ocurrence ocurrence) {
        this.ocurrences.add(ocurrence);
    }

    public String getName() {
        return this.name;
    }

    public Map<String, Class> getParameters() {
        return this.parameters;
    }

    public List<Ocurrence> getOcurrences() {
        return this.ocurrences;
    }

}
