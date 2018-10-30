package models.managers;

import models.resource.Model;
import models.resource.Ocurrence;
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

    private List<Resource> resources;
    private List<Model> models;

    private DataManager() {
        this.resources = new ArrayList<>();
        this.models = new ArrayList<>();
        setupData();
    }

    // Hardcoded data for test purposes
    private void setupData() {

        Map<String, Class> params = new HashMap<>();
        params.put("Name", String.class);
        params.put("Age", int.class);

        List<Ocurrence> ocurrences = new ArrayList<>();
        ocurrences.add(new Ocurrence("Dead"));

        Model model = new Model("User", params, ocurrences);
        models.add(model);

        Map<String, Object> values = new HashMap<>();
        values.put("Name", "user");
        values.put("Age", 21);

        resources.add(new Resource("User 1", model, 1, values));
        resources.add(new Resource("User 2", model, 1, values));
        resources.add(new Resource("User 3", model, 1, values));
    }

    public List<Resource> getResources() {
        return this.resources;
    }

    public List<Model> getModels() {
        return this.models;
    }

}
