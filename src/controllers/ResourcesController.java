package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    private void setupTable() {

        TableColumn<Resource, String> nameColumn = (TableColumn) resourcesTable.getColumns().get(0);
        TableColumn<Resource, String> modelColumn = (TableColumn) resourcesTable.getColumns().get(1);
        TableColumn<Resource, String> sectionColumn = (TableColumn) resourcesTable.getColumns().get(2);
        TableColumn<Resource, String> ocurrencesColumn = (TableColumn) resourcesTable.getColumns().get(3);

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        modelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModel().getName()));
        sectionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getSection())));
        ocurrencesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getOcurrences().size())));

        resourcesTable.setItems(data);
        resourcesTable.setPlaceholder(new Label("No resources registered"));
    }
}
