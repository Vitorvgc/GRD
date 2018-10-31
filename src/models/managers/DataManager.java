package models.managers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.resource.Model;
import models.resource.OcurrenceType;
import models.resource.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {

    private static DataManager instance = new DataManager();

    public static DataManager getInstance() {
        return instance;
    }

    private ObservableList<Resource> resources;
    private ObservableList<Model> models;

    private DataManager() {
        this.resources = FXCollections.observableArrayList();
        this.models = FXCollections.observableArrayList();
        setupData();
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

        List<OcurrenceType> ocurrences1 = new ArrayList<>();
        ocurrences1.add(new OcurrenceType("Atraso"));
        ocurrences1.add(new OcurrenceType("Falta"));
        ocurrences1.add(new OcurrenceType("Acidente"));

        Model model1 = new Model("Funcionário", params1, ocurrences1);

        models.add(model1);

        // Máquina model

        Map<String, Class> params2 = new HashMap<>();
        params2.put("Nome", String.class);
        params2.put("Setor", int.class);

        List<OcurrenceType> ocurrences2 = new ArrayList<>();
        ocurrences2.add(new OcurrenceType("Mal funcionamento"));
        ocurrences2.add(new OcurrenceType("Defeito"));

        Model model2 = new Model("Máquina", params2, ocurrences2);

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
    }

    public ObservableList<Resource> getResources() {
        return this.resources;
    }

    public ObservableList<Model> getModels() {
        return this.models;
    }

}
