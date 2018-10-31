package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import models.resource.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ResourceController {

    @FXML
    private Label resourceNameLabel,
                  resourceModelLabel;

    @FXML
    private ToggleGroup contentTabs;

    @FXML
    private ToggleButton informationToggleButton,
                         ocurrencesToggleButton;

    @FXML
    private ListView<String> ocurrencesList;

    @FXML
    private TableView< Map.Entry<String, Object> > informationTable;

    @FXML
    private Pane contentContainer;

    private Resource resource;

    public ResourceController(Resource resource) {
        this.resource = resource;
    }

    @FXML
    private void initialize() {

        setupTitle();
        setupToggleButtons();
        setupInformationTable();
        setupOcurrencesList();
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

    private void setupToggleButtons() {

        contentTabs.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue == null)
                oldValue.setSelected(true);
            else if(informationToggleButton.isSelected()) {
                informationTable.setVisible(true);
                ocurrencesList.setVisible(false);
            }
            else if(ocurrencesToggleButton.isSelected()) {
                informationTable.setVisible(false);
                ocurrencesList.setVisible(true);
            }
        });
    }

    private void setupInformationTable() {

        TableColumn< Map.Entry<String, Object>, String > fieldColumn = (TableColumn) informationTable.getColumns().get(0);
        TableColumn< Map.Entry<String, Object>, String > valueColumn = (TableColumn) informationTable.getColumns().get(1);

        fieldColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        valueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));

        ArrayList< Map.Entry<String, Object> > information = new ArrayList<>(resource.getData().entrySet());
        informationTable.setItems(FXCollections.observableArrayList(information));
    }

    private void setupOcurrencesList() {

        ocurrencesList.setPlaceholder(new Label("Nenhuma ocorrência registrada"));

        List<String> ocurrences = resource.getOcurrences().stream()
                .map(ocurrence -> ocurrence.getType().getTitle())
                .collect(Collectors.toList());

        ocurrencesList.setItems(FXCollections.observableArrayList(new ArrayList<String>(ocurrences)));
    }
}
