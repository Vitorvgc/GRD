package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.managers.DataManager;
import models.resource.Model;

import java.io.IOException;

public class ModelsController {

    @FXML
    private TableView<Model> modelsTable;

    @FXML
    private void initialize() {
        setupTable();
    }

    @FXML
    private void addModel() {}

    private void setupTable() {

        TableColumn<Model, String> nameColumn = (TableColumn) modelsTable.getColumns().get(0);
        TableColumn<Model, String> parametersColumn = (TableColumn) modelsTable.getColumns().get(1);
        TableColumn<Model, String> occurrencesColumn = (TableColumn) modelsTable.getColumns().get(2);

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        parametersColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getParameters().keySet().size())));
        occurrencesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getOccurrenceTypes().size())));

        modelsTable.setPlaceholder(new Label("Nenhum modelo registrado"));
        modelsTable.setItems(DataManager.getInstance().getModels());
    }
}
