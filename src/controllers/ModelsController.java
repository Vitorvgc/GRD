package controllers;

import database.ModelDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import models.resource.Model;
import util.TableUpdater;

import java.io.IOException;

public class ModelsController implements TableUpdater {

    @FXML
    private TableView<Model> modelsTable;

    @FXML
    private void initialize() {
        setupTable();
    }

    @FXML
    private void addModel() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/createModel.fxml"));
        CreateModelController controller = new CreateModelController ();
        loader.setController(controller);
        stage.setTitle("Adicionar Modelo");
        try {
            stage.setScene(new Scene(loader.load(), 430, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller.initialize(this);
        stage.show();
    }

    private void showModel(Model model) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/model.fxml"));
            ModelController controller = new ModelController(model);
            loader.setController(controller);
            Pane pane = loader.load();
            Stage stage = new Stage();
            stage.setTitle(model.getName());
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupTable() {

        TableColumn<Model, String> nameColumn = (TableColumn) modelsTable.getColumns().get(0);
        TableColumn<Model, String> parametersColumn = (TableColumn) modelsTable.getColumns().get(1);
        TableColumn<Model, String> occurrencesColumn = (TableColumn) modelsTable.getColumns().get(2);

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        parametersColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getParameters().size())));
        occurrencesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getOccurrenceTypes().size())));

        // double click event on row -> show clicked resource
        modelsTable.setRowFactory(tableView -> {
            TableRow<Model> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    Model model = row.getItem();
                    showModel(model);
                }
            });
            return row;
        });

        modelsTable.setPlaceholder(new Label("Nenhum modelo registrado"));
        modelsTable.setItems(FXCollections.observableArrayList(new ModelDAO().getAll()));
    }

    @Override
    public void updateTable() {
        modelsTable.setItems(FXCollections.observableArrayList(new ModelDAO().getAll()));
    }
}
