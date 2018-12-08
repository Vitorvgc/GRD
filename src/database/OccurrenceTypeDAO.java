package database;

import models.resource.Model;
import models.resource.OccurrenceType;
import util.StringFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OccurrenceTypeDAO {

    private Connection connection;

    public OccurrenceTypeDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void add(OccurrenceType occurrenceType, Model model) {
        checkOccurrenceTypeTable();

        String sql = "insert into Occurrence_Type (name, model) values (?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,
                    StringFormatter.codeFormat(occurrenceType.getTitle()));
            statement.setString(2,
                    StringFormatter.codeFormat(model.getName()));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<OccurrenceType> getByModelName(String model) throws RuntimeException {
        String sql = "select name from Occurrence_Type where model = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, StringFormatter.codeFormat(model));

            ResultSet resultSet = statement.executeQuery();

            ArrayList<OccurrenceType> types = new ArrayList<>();
            while (resultSet.next()) {
                String type = resultSet.getString("name");
                types.add(new OccurrenceType(StringFormatter.codeFormat(type)));
            }
            return types;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkOccurrenceTypeTable() {
        String sql = "select COLUMN_NAME from information_schema.columns " +
                     "where TABLE_SCHEMA = ? and TABLE_NAME = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "GRD");
            statement.setString(2, "Occurrence_Type");
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                String sqlCreate = "CREATE TABLE Occurrence_Type (" +
                        "id INT AUTO_INCREMENT, " +
                        "name VARCHAR(80) NOT NULL, " +
                        "model VARCHAR(80) NOT NULL, " +
                        "PRIMARY KEY (id));";
                PreparedStatement stm = connection.prepareStatement(sqlCreate);
                stm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getId(OccurrenceType type) {
        String sql = "select id from Occurrence_Type where name = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, StringFormatter.codeFormat(type.getTitle()));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                return id;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public OccurrenceType getById(int id) throws SQLException {
        OccurrenceType type = null;
        String sql = "select name from Occurrence_Type where id = " + id + ";";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultId = statement.executeQuery();
        while (resultId.next()) {
            String typeName = resultId.getString("name");
            type = new OccurrenceType(StringFormatter.userFormat(typeName));
        }
        return type;
    }
}
