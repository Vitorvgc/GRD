package database;

import javafx.util.Pair;
import models.resource.Model;
import models.resource.OccurrenceType;
import util.TypeName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ModelDAO {

    private Connection connection;

    public ModelDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public Model get(String modelName) throws RuntimeException {

        String sql = "select COLUMN_NAME, DATA_TYPE from information_schema.columns " +
                     "where TABLE_SCHEMA = ? and TABLE_NAME = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "GRD");
            statement.setString(2, modelName.replace(' ', '_'));

            OccurrenceTypeDAO occurrenceTypeDAO = new OccurrenceTypeDAO();

            ResultSet resultSet = statement.executeQuery();

            List<Pair<String, Class>> parameters = getParameters(resultSet);
            List<OccurrenceType> occurrenceTypes = occurrenceTypeDAO.
                    getByModelName(modelName.replace(' ', '_'));

            return new Model(modelName.replace('_', ' '), parameters, occurrenceTypes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Model> getAll() throws RuntimeException {

        String sql = "select distinct TABLE_NAME from information_schema.columns where TABLE_SCHEMA = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "GRD");

            ResultSet resultSet = statement.executeQuery();

            ArrayList<Model> models = new ArrayList<>();
            while (resultSet.next()) {
                String tableName = resultSet.getString(1);
                if (tableName.equals("OccurrenceType"))
                    continue;
                models.add(get(tableName));
            }
            return models;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Model model) {

        String sql = "create table " + model.getName().replace(' ', '_') +
                "(id int auto_increment, ";
        for (Pair<String, Class> parameter : model.getParameters()) {
            sql += parameter.getKey() + " ";
            sql += TypeName.fromJavaClass(parameter.getValue()).toSQLDataType() + " not null, ";
        }
        sql += "primary key (id));";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Pair<String, Class>> getParameters(ResultSet resultSet) {

        try {
            ArrayList<Pair<String, Class>> parameters = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString(1).toLowerCase().replace('_', ' ');
                if (name.equals("id"))
                    continue;
                String type = resultSet.getString(2);
                Class typeClass = TypeName.fromSQLDataType(type).toJavaClass();
                if (typeClass != null)
                    parameters.add(new Pair<>(name, typeClass));
            }

            return parameters;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
