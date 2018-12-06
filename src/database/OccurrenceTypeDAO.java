package database;

import models.resource.Model;
import models.resource.OccurrenceType;
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

        String sql = "insert into OccurrenceType (name, model) values (?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,
                    occurrenceType.getTitle().replace(' ', '_'));
            statement.setString(2,
                    model.getName().replace(' ', '_'));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<OccurrenceType> getByModelName(String model) throws RuntimeException {
        String sql = "select name from OccurrenceType where model = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, model.replace(' ', '_'));

            ResultSet resultSet = statement.executeQuery();

            ArrayList<OccurrenceType> types = new ArrayList<>();
            while (resultSet.next()) {
                String type = resultSet.getString("name");
                types.add(new OccurrenceType(type.replace('_', ' ')));
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
            statement.setString(2, "OccurrenceType");
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                String sqlCreate = "create table OccurrenceType " +
                                   "(id int auto_increment, " +
                                   "name varchar(45) not null, " +
                                   "model varchar(45) not null, " +
                                   "primary key (id));";
                PreparedStatement stm = connection.prepareStatement(sqlCreate);
                stm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
