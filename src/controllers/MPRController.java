package controllers;

import database.GuidelineDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.guideline.Guideline;
import util.TableUpdater;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MPRController implements TableUpdater {

    private GridPane gridpane = new GridPane();

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label emptyPaneLabel;

    @FXML
    private void initialize() {

        gridpane.setHgap(15);
        gridpane.setVgap(10);
        gridpane.getColumnConstraints().add(new ColumnConstraints(280));
        gridpane.getColumnConstraints().add(new ColumnConstraints(280));
        scrollPane.setContent(gridpane);
        updateTable();

//        if (Desktop.isDesktopSupported()) {
//            try {
//                File myFile = new File("./src/controllers/Comprovante.pdf");
//                FileInputStream input = new FileInputStream(myFile);
//                //Desktop.getDesktop().open(myFile);
//                Guideline guideline = new Guideline("BOM", myFile, GuidelineType.EMERGENCY);
//                GuidelineDAO dao = new GuidelineDAO();
//                dao.add(guideline);
//            } catch (IOException ex) {
//                System.out.println("No application registered for PDFs");
//            }
//        }
    }

    @FXML
    private void onAddGuidelineClicked() {

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/createGuideline.fxml"));
        CreateGuidelineController controller = new CreateGuidelineController ();
        loader.setController(controller);
        stage.setTitle("Adicionar Medida");
        try {
            stage.setScene(new Scene(loader.load(), 430, 315));
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller.init(this);
        stage.show();
    }

    @Override
    public void updateTable() {

        GuidelineDAO dao = new GuidelineDAO();
        List<Guideline> guidelines = dao.getAll();
        gridpane.getChildren().clear();

        if (guidelines.size() < 1) {
            emptyPaneLabel.setVisible(true);
            return;
        } else emptyPaneLabel.setVisible(false);

        for (int i = 0; i <= guidelines.size() / 2; ++i)
            for (int j = 0; j < 2; ++j) {
                if (i * 2 + j >= guidelines.size()) break;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/guideline.fxml"));
                GuidelineController controller = new GuidelineController();
                loader.setController(controller);
                try {
                    gridpane.add(loader.load(), j, i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    controller.init(guidelines.get(i * 2 + j));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
    }
}
