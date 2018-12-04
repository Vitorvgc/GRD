package database;

import models.resource.Model;
import models.resource.Resource;

import java.sql.Connection;
import java.util.List;

public class ResourceDAO {

    private Connection connection;

    public ResourceDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public Resource get(Model model, int id) {
        return null;
    }

    public List<Resource> getAll() {
        return null;
    }

    public void add(Resource resource) {

    }

    public void delete(Resource resource) {

    }
}
