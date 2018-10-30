package models.managers;

import models.resource.Model;
import models.resource.Resource;

import java.util.ArrayList;
import java.util.List;

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

    private void setupData() {

    }

    public List<Resource> getResources() {
        return this.resources;
    }

    public List<Model> getModels() {
        return this.models;
    }

}
