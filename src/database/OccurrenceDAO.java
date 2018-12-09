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
import java.util.Date;
import java.util.List;

public class OccurrenceDAO {

    private Connection connection;

    public OccurrenceDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void add(Occurrence occurrence, Resource resource) {
        String modelName = StringFormatter.codeFormat(resource.getModel().getName());

        String sql = "insert into " + modelName + "_Occurrence_ (" +
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

    public List<Occurrence> getAllFrom(Resource resource) {
        String modelName = StringFormatter.codeFormat(resource.getModel().getName());
        String tableName = modelName + "_Occurrence_";
        String sql = "select * from " + tableName + " where idResource = " + resource.getId() + ";";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Occurrence> occurrences = new ArrayList<>();
            while (resultSet.next()) {
                String details = resultSet.getString("details");
                Date date = resultSet.getDate("date");
                int idType = resultSet.getInt("idType");
                OccurrenceType type = new OccurrenceTypeDAO().getById(idType);
                occurrences.add(new Occurrence(type, date, details));
            }
            return occurrences;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countFrom(Model model) {

        String tableName = StringFormatter.codeFormat(model.getName()) + "_Occurrence_";

        String sql = "select count(*) from " + tableName + ";";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            if (result.next())
                return result.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
