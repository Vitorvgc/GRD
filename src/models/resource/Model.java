package models.resource;

import java.util.List;
import java.util.Map;

public class Model {

    private String name;
    private Map<String, Class> parameters;
    private List<OccurrenceType> occurrenceTypes;

    public Model(String name, Map<String, Class> parameters, List<OccurrenceType> occurrenceTypes) {
        this.name = name;
        this.parameters = parameters;
        this.occurrenceTypes = occurrenceTypes;
    }

    public void insertOcurrence(OccurrenceType occurrenceType) {
        this.occurrenceTypes.add(occurrenceType);
    }

    public String getName() {
        return this.name;
    }

    public Map<String, Class> getParameters() { return this.parameters; }

    public List<OccurrenceType> getOccurrenceTypes() {
        return this.occurrenceTypes;
    }

}
