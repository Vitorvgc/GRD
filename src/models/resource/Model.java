package models.resource;

import java.util.List;
import java.util.Map;

public class Model {

    private String name;
    private Map<String, Class> parameters;
    private List<OcurrenceType> ocurrenceTypes;

    public Model(String name, Map<String, Class> parameters, List<OcurrenceType> ocurrenceTypes) {
        this.name = name;
        this.parameters = parameters;
        this.ocurrenceTypes = ocurrenceTypes;
    }

    public void insertOcurrence(OcurrenceType ocurrenceType) {
        this.ocurrenceTypes.add(ocurrenceType);
    }

    public String getName() {
        return this.name;
    }

    public Map<String, Class> getParameters() {
        return this.parameters;
    }

    public List<OcurrenceType> getOcurrenceTypes() {
        return this.ocurrenceTypes;
    }

}
