package models.managers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.resource.Model;
import models.resource.OccurrenceType;
import models.resource.Occurrence;
import models.resource.Resource;
import java.util.*;

public class DataManager {

    private static DataManager instance = new DataManager();

    public static DataManager getInstance() {
        return instance;
    }

    private ObservableList<Resource> resources;
    private ObservableList<Model> models;
    private Map<Class, String> typeNames;

    private DataManager() {
        this.resources = FXCollections.observableArrayList();
        this.models = FXCollections.observableArrayList();
        setupTypeNames();
        setupData();
    }

    // Stores user-friendly names for the types of parameter in models
    public void setupTypeNames() {
        this.typeNames = new Hashtable<Class, String>();
        typeNames.put(int.class, "Número");
        typeNames.put(String.class, "Texto");
    }

    // Hardcoded data for test purposes
    private void setupData() {

        // Funcionário model

        Map<String, Class> params1 = new HashMap<>();
        params1.put("Nome", String.class);
        params1.put("Idade", int.class);
        params1.put("Setor", int.class);
        params1.put("Turno", String.class);
        params1.put("Salário", String.class);

        List<OccurrenceType> occurrences1 = new ArrayList<>();
        occurrences1.add(new OccurrenceType("Atraso"));
        occurrences1.add(new OccurrenceType("Falta"));
        occurrences1.add(new OccurrenceType("Acidente"));

        Model model1 = new Model("Funcionário", params1, occurrences1);

        models.add(model1);

        // Máquina model

        Map<String, Class> params2 = new HashMap<>();
        params2.put("Nome", String.class);
        params2.put("Setor", int.class);

        List<OccurrenceType> occurrences2 = new ArrayList<>();
        occurrences2.add(new OccurrenceType("Mal funcionamento"));
        occurrences2.add(new OccurrenceType("Defeito"));

        Model model2 = new Model("Máquina", params2, occurrences2);

        models.add(model2);

        // Resources

        Map<String, Object> values1 = new HashMap<>();
        values1.put("Nome", "João");
        values1.put("Idade", 23);
        values1.put("Setor", 2);
        values1.put("Turno", "Manhã");
        values1.put("Salário", "R$ 2500,00");

        Map<String, Object> values2 = new HashMap<>();
        values2.put("Nome", "Maria");
        values2.put("Idade", 22);
        values2.put("Setor", 3);
        values2.put("Turno", "Tarde");
        values2.put("Salário", "R$ 2700,00");

        Map<String, Object> values3 = new HashMap<>();
        values3.put("Nome", "José");
        values3.put("Idade", 21);
        values3.put("Setor", 1);
        values3.put("Turno", "Manhã");
        values3.put("Salário", "R$ 2300,00");

        Map<String, Object> values4 = new HashMap<>();
        values4.put("Nome", "Máquina 1");
        values4.put("Setor", 1);

        Map<String, Object> values5 = new HashMap<>();
        values5.put("Nome", "Máquina 2");
        values5.put("Setor", 2);

        resources.add(new Resource("João", model1, 2, values1));
        resources.add(new Resource("Maria", model1, 3, values2));
        resources.add(new Resource("José", model1, 1, values3));
        resources.add(new Resource("Máquina 1", model2, 1, values4));
        resources.add(new Resource("Máquina 2", model2, 2, values5));

        // Occurrences

        resources.get(0).addOccurrence(new Occurrence(new OccurrenceType("Falta"), new Date(), ""));
        resources.get(0).addOccurrence(new Occurrence(new OccurrenceType("Atraso"), new Date(), "30 minutos de atraso"));
        resources.get(0).addOccurrence(new Occurrence(new OccurrenceType("Acidente"), new Date(), ""));

        resources.get(1).addOccurrence(new Occurrence(new OccurrenceType("Atraso"), new Date(), ""));
        resources.get(1).addOccurrence(new Occurrence(new OccurrenceType("Atraso"), new Date(), ""));

        resources.get(2).addOccurrence(new Occurrence(new OccurrenceType("Acidente"), new Date(), "Choque elétrico"));
        resources.get(2).addOccurrence(new Occurrence(new OccurrenceType("Acidente"), new Date(), "Cortes profundos no braço direito"));
        resources.get(2).addOccurrence(new Occurrence(new OccurrenceType("Acidente"), new Date(), "Fratura da clavícula"));
        resources.get(2).addOccurrence(new Occurrence(new OccurrenceType("Acidente"), new Date(), "Queimadura de terceiro grau"));
        resources.get(2).addOccurrence(new Occurrence(new OccurrenceType("Falta"), new Date(), ""));
    }

    public ObservableList<Resource> getResources() {
        return this.resources;
    }

    public ObservableList<Model> getModels() {
        return this.models;
    }

    public Map<Class, String> getTypeNames() { return typeNames; }

    public Class getKeyByValue(String value) {
        for (Map.Entry<Class, String> entry : typeNames.entrySet())
            if (Objects.equals(value, entry.getValue()))
                return entry.getKey();
        return null;
    }
}
