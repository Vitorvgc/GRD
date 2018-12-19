package database;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import com.mysql.cj.jdbc.exceptions.PacketTooBigException;
import models.guideline.Guideline;
import models.guideline.GuidelineType;
import util.AlertHelper;
import util.StringFormatter;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuidelineDAO {

    private Connection connection;

    public GuidelineDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void add(Guideline guideline, String contentExtension, String previewExtension) {

        checkGuidelineTable();

        PreparedStatement statement;
        String sql = "insert into Guideline_ (title, content, preview, type, contentExtension, previewExtension)" +
                "values (?, ?, ?, ?, ?, ?)";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, StringFormatter.codeFormat(guideline.getTitle()));
            statement.setBinaryStream(2, guideline.getContentStream());
            statement.setBinaryStream(3, guideline.getPreviewStream());
            statement.setString(4, guideline.getType().toString());
            statement.setString(5, contentExtension);
            statement.setString(6, previewExtension);
            statement.execute();

        } catch (PacketTooBigException e) {
            AlertHelper.error("Arquivo muito grande",
                    "O arquivo escolhido excede o tamanho limite suportado");
        } catch (MysqlDataTruncation e) {
            AlertHelper.error("Arquivo muito grande",
                    "O arquivo escolhido excede o tamanho limite suportado");
        } catch (SQLException e) {
            AlertHelper.error("Erro",
                    "Um erro ocorreu no banco e a medida não foi salva");
        } catch (FileNotFoundException e) {
            AlertHelper.error("Arquivo não encontrado",
                    "O arquivo escolhido não foi encontrado");
        }
    }

    public List<Guideline> getAll() {

        checkGuidelineTable();

        String sql = "select * from Guideline_;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Guideline> guidelines = new ArrayList<>();
            while (resultSet.next()) {
                String title = StringFormatter.userFormat(resultSet.getString("title"));
                String type = resultSet.getString("type");
                String contentExtension = resultSet.getString("contentExtension");
                String previewExtension = resultSet.getString("previewExtension");

                InputStream contentInput = resultSet.getBinaryStream("content");
                File contentFile = new File("./src/guidelines/temp." + contentExtension);
                FileOutputStream contentOutput = new FileOutputStream(contentFile);
                byte[] contentBuffer = new byte[1024];
                while (contentInput.read(contentBuffer) > 0)
                    contentOutput.write(contentBuffer);

                InputStream previewInput = resultSet.getBinaryStream("preview");
                File previewFile = new File("./src/guidelines/tempPreview." + previewExtension);
                FileOutputStream previewOutput = new FileOutputStream(previewFile);
                byte[] previewBuffer = new byte[1024];
                while (previewInput.read(previewBuffer) > 0)
                    previewOutput.write(previewBuffer);

                Guideline guideline = new Guideline(title, contentFile, previewFile,
                        GuidelineType.fromSQLDataType(type));
                guidelines.add(guideline);
            }
            return guidelines;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void checkGuidelineTable() {

        String sql = "select COLUMN_NAME from information_schema.columns " +
                "where TABLE_SCHEMA = ? and TABLE_NAME = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "GRD");
            statement.setString(2, "Guideline_");
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                String sqlCreate = "CREATE TABLE Guideline_ (" +
                        "id INT AUTO_INCREMENT, " +
                        "title VARCHAR(80) NOT NULL, " +
                        "content LONGBLOB NOT NULL, " +
                        "preview LONGBLOB NOT NULL, " +
                        "type VARCHAR(80) NOT NULL, " +
                        "previewExtension VARCHAR(5) NOT NULL, " +
                        "contentExtension VARCHAR(5) NOT NULL, " +
                        "PRIMARY KEY (id));";
                PreparedStatement stm = connection.prepareStatement(sqlCreate);
                stm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
