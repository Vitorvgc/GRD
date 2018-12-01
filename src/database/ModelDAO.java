package database;

import models.resource.Model;
import models.resource.OccurrenceType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
