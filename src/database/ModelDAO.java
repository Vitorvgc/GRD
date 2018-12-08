package database;

import javafx.util.Pair;
import models.resource.Model;
import models.resource.OccurrenceType;
import util.StringFormatter;
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

        modelName = StringFormatter.codeFormat(modelName);

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "GRD");
            statement.setString(2, modelName);

            OccurrenceTypeDAO occurrenceTypeDAO = new OccurrenceTypeDAO();

            ResultSet resultSet = statement.executeQuery();

            List<Pair<String, Class>> parameters = getParameters(resultSet);
            List<OccurrenceType> occurrenceTypes = occurrenceTypeDAO.
                    getByModelName(modelName);

            return new Model(modelName, parameters, occurrenceTypes);
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
                if (tableName.contains("Occurrence_"))
                    continue;
                models.add(get(tableName));
            }
            return models;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Model model) {

        String modelCodename = StringFormatter.codeFormat(model.getName());
        String sqlModel = "create table " + modelCodename +
                "(id int auto_increment, ";
        for (Pair<String, Class> parameter : model.getParameters()) {
            sqlModel += parameter.getKey() + " ";
            sqlModel += TypeName.fromJavaClass(parameter.getValue()).toSQLDataType() + " not null, ";
        }
        sqlModel += "primary key (id));";

        String sqlOccurrence = "create table " + modelCodename + "_Occurrence_ " +
                "(id int auto_increment, idType int not null, idResource int not null," +
                "details varchar(100), date date, primary key (id));";
        try {
            PreparedStatement statementModel = connection.prepareStatement(sqlModel);
            statementModel.execute();

            PreparedStatement statementOccurrence = connection.prepareStatement(sqlOccurrence);
            statementOccurrence.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Pair<String, Class>> getParameters(ResultSet resultSet) {

        try {
            ArrayList<Pair<String, Class>> parameters = new ArrayList<>();
            while (resultSet.next()) {
                String name = StringFormatter.codeFormat(resultSet.getString(1));
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
