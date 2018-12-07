package database;

import models.resource.Model;
import models.resource.Occurrence;
import models.resource.OccurrenceType;
import models.resource.Resource;
import util.StringFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OccurrenceDAO {

    private Connection connection;

    public OccurrenceDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void add(Occurrence occurrence, Resource resource) {
        Model model = resource.getModel();

        String sql = "insert into " + StringFormatter.codeFormat(model.getName()) + "_Occurrence_ (" +
                "idType, idResource, details, date) values (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, new OccurrenceTypeDAO().getId(occurrence.getType()));
            statement.setInt(2, resource.getId());
            statement.setString(3, occurrence.getDetails());
            statement.setDate(4, new java.sql.Date(occurrence.getDate().getTime()));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<OccurrenceType> get(Model model, OccurrenceType occurrenceType, int id) {
        String tableName = String.join("-",
                StringFormatter.codeFormat(model.getName()),
                StringFormatter.codeFormat(occurrenceType.getTitle()),
                String.valueOf(id));

        String sql = "select * from " + tableName;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, StringFormatter.codeFormat(model.getName()));

            ResultSet resultSet = statement.executeQuery();

            ArrayList<OccurrenceType> types = new ArrayList<>();
            while (resultSet.next()) {
                String type = resultSet.getString("name");
                types.add(new OccurrenceType(StringFormatter.userFormat(type)));
            }
            return types;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
