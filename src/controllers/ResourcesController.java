package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import models.resource.Resource;


public class ResourcesController {

    @FXML
    private TableView<Resource> resourcesTable;

    private ObservableList<Resource> data = FXCollections.observableArrayList(DataManager.getInstance().getResources());

    @FXML
    private void initialize() {
        setupTable();
    }

    @FXML
    private void addResource() {
        System.out.println("Add resource");
    }

    private void showResource(Resource resource) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/resource.fxml"));
            ResourceController controller = new ResourceController(resource);
            loader.setController(controller);
            Pane pane = loader.load();
            Stage stage = new Stage();
            stage.setTitle(resource.getName());
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupTable() {

        TableColumn<Resource, String> nameColumn = (TableColumn) resourcesTable.getColumns().get(0);
        TableColumn<Resource, String> modelColumn = (TableColumn) resourcesTable.getColumns().get(1);
        TableColumn<Resource, String> sectionColumn = (TableColumn) resourcesTable.getColumns().get(2);
        TableColumn<Resource, String> ocurrencesColumn = (TableColumn) resourcesTable.getColumns().get(3);

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        modelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModel().getName()));
        sectionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getSection())));
        ocurrencesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getOcurrences().size())));

        // double click event on row -> show clicked resource
        resourcesTable.setRowFactory(tableView -> {
            TableRow<Resource> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    Resource resource = row.getItem();
                    showResource(resource);
                }
            });
            return row;
        });

        resourcesTable.setPlaceholder(new Label("Nenhum recurso registrado"));
        resourcesTable.setItems(data);

    }
}
