package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.resource.Model;
import models.resource.OccurrenceType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelDAO {

    private Connection connection;

    public ModelDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void add(Model model) {
        String sql = "insert into Model " +
                     "(name,parameters,occurrences) " +
                     "values (?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            String parameters = "", types = "";
            boolean first = true;
            for (String param : model.getParameters().keySet()) {
                if (!first) parameters += ";";
                else first = false;
                String className = model.getParameters().get(param) == String.class ? "Text" : "Number";
                parameters += param + ":" + className;
            }
            first = true;
            for (OccurrenceType type : model.getOccurrenceTypes()) {
                if (!first) types += ";";
                else first = false;
                types += type.getTitle();
            }
            stmt.setString(1, model.getName());
            stmt.setString(2, parameters);
            stmt.setString(3, types);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Model> getAll() {
        ObservableList<Model> models = FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = connection.prepareStatement("select * from Model");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Model model;
                String name = rs.getString("name");
                String paramString = rs.getString("parameters");
                String[] paramList = paramString.split("[:;]");
                Map<String, Class> parameters = new HashMap<>();
                for (int i = 0; i < paramList.length; i += 2) {
                    Class paramClass = paramList[i + 1].compareTo("Text") == 0 ? String.class : int.class;
                    parameters.put(paramList[i], paramClass);
                }
                String occurString = rs.getString("occurrences");
                String[] occurList = occurString.split("[;]");
                List<OccurrenceType> occurrences = new ArrayList<>();
                for (String occurrence : occurList) {
                    OccurrenceType type = new OccurrenceType(occurrence);
                    occurrences.add(type);
                }
                model = new Model(name, parameters, occurrences);
                models.add(model);
            }
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return models;
    }
}
