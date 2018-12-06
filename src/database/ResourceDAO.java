package database;

import javafx.util.Pair;
import models.resource.Model;
import models.resource.Resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResourceDAO {

    private Connection connection;

    public ResourceDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public Resource get(Model model, int id) {

        String[] modelParameters = model.getParameters().stream().map(Pair::getKey).toArray(String[]::new);
        String params = String.join(", ", modelParameters);

        String sql = "select " + params + " from " + model.getName() + " where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            List<Pair<String, Object>> data = getData(model, result);
            if (data != null) {
                Resource resource = new Resource(model, data);
                resource.setId(id);
                return resource;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Resource> getAll() {

        ArrayList<Resource> resources = new ArrayList<>();
        List<Model> models = new ModelDAO().getAll();

        for (Model model : models) {

            String sql = "select id from " + model.getName();

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery();

                while (result.next()) {
                    int id = result.getInt(1);
                    Resource resource = get(model, id);
                    resources.add(resource);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resources;
    }

    public void add(Resource resource) {

    }

    public void delete(Resource resource) {

    }

    private List<Pair<String, Object>> getData(Model model, ResultSet resultSet) {

        try {
            ArrayList<Pair<String, Object>> data = new ArrayList<>();

            if (resultSet.next()) {
                for (int i = 0; i < model.getParameters().size(); ++i) {
                    String key = model.getParameters().get(i).getKey();
                    Object value = resultSet.getObject(i + 1);
                    data.add(new Pair<>(key, value));
                }
                return data;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
