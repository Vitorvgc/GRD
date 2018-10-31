package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.resource.Resource;

import java.util.ArrayList;
import java.util.Map;


public class ResourceController {

    @FXML
    private Label resourceNameLabel,
                  resourceModelLabel;

    @FXML
    private TableView< Map.Entry<String, Object> > infoTable;

    private Resource resource;

    public ResourceController(Resource resource) {
        this.resource = resource;
    }

    @FXML
    private void initialize() {
        setupTitle();
        setupTable();
    }

    @FXML
    private void didPressUpdateDataButton() {

    }

    @FXML
    private void didPressAddOcurrenceButton() {

    }

    @FXML
    private void didPressDeleteResourceButton() {

    }

    private void setupTitle() {
        resourceNameLabel.setText(resource.getName());
        resourceModelLabel.setText(resource.getModel().getName());
    }

    private void setupTable() {

        TableColumn< Map.Entry<String, Object>, String > fieldColumn = (TableColumn) infoTable.getColumns().get(0);
        TableColumn< Map.Entry<String, Object>, String > valueColumn = (TableColumn) infoTable.getColumns().get(1);

        fieldColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        valueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));

        ArrayList< Map.Entry<String, Object> > information = new ArrayList<>(resource.getData().entrySet());
        infoTable.setItems(FXCollections.observableArrayList(information));
    }
}
