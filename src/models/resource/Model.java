package models.resource;

import javafx.util.Pair;
import java.util.List;

public class Model {

    private String name;
    private List<Pair<String, Class>> parameters;
    private List<OccurrenceType> occurrenceTypes;

    public Model(String name, List<Pair<String, Class>> parameters, List<OccurrenceType> occurrenceTypes) {

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

    public List<Pair<String, Class>> getParameters() { return this.parameters; }

    public List<OccurrenceType> getOccurrenceTypes() {
        return this.occurrenceTypes;
    }

}
