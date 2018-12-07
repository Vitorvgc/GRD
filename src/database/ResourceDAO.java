package database;

import javafx.util.Pair;
import models.resource.Model;
import models.resource.Resource;
import util.StringFormatter;

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

        String[] modelParameters = model.getParameters().stream()
                .map(param -> StringFormatter.codeFormat(param.getKey()))
                .toArray(String[]::new);
        String params = String.join(", ", modelParameters);

        String sql = "select " + params + " from " + StringFormatter.codeFormat(model.getName()) + " where id = ?";

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

            String sql = "select id from " + StringFormatter.codeFormat(model.getName());

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

        Model model = resource.getModel();
        String[] modelParameters = model.getParameters().stream()
                .map(param -> StringFormatter.codeFormat(param.getKey()))
                .toArray(String[]::new);
        String params = String.join(", ", modelParameters);

        String values = "(";
        for (int i = 0; i < modelParameters.length; ++i) {
            values += "?";
            values += (i == modelParameters.length - 1 ? ")" : ", ");
        }

        String sql = "insert into " + StringFormatter.codeFormat(model.getName()) + " (" + params + ") " +
                "values " + values;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            for (int i = 0; i < resource.getData().size(); ++i) {

                String name = resource.getData().get(i).getKey();
                Object value = resource.getData().get(i).getValue();
                Class type = model.getParameters().stream()
                        .filter(param -> param.getKey().equals(name))
                        .findFirst().get().getValue();

                if (type == int.class)
                    statement.setInt(i + 1, Integer.parseInt(value.toString()));
                else
                    statement.setString(i + 1, value.toString());
            }

            statement.execute();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
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
