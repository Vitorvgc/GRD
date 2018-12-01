package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import models.managers.DataManager;
import models.resource.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModelController {

    @FXML
    private Label modelNameLabel;

    @FXML
    private ToggleGroup contentTabs;

    @FXML
    private ToggleButton parametersToggleButton,
                         occurrencesToggleButton;

    @FXML
    private ListView<String> occurrencesList;

    @FXML
    private TableView< Map.Entry<String, Class> > parametersTable;

    @FXML
    private Pane contentContainer;

    private Model model;

    public ModelController(Model model) {
        this.model = model;
    }

    @FXML
    private void initialize() {

        setupTitle();
        setupToggleButtons();
        setupParametersTable();
        setupOcurrencesList();
    }

    private void setupTitle() {
        modelNameLabel.setText(model.getName());
    }

    private void setupToggleButtons() {

        contentTabs.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue == null)
                oldValue.setSelected(true);
            else if(parametersToggleButton.isSelected()) {
                parametersTable.setVisible(true);
                occurrencesList.setVisible(false);
            }
            else if(occurrencesToggleButton.isSelected()) {
                parametersTable.setVisible(false);
                occurrencesList.setVisible(true);
            }
        });
    }

    private void setupParametersTable() {

        TableColumn< Map.Entry<String, Class>, String > fieldColumn = (TableColumn) parametersTable.getColumns().get(0);
        TableColumn< Map.Entry<String, Class>, String > typeColumn = (TableColumn) parametersTable.getColumns().get(1);

        fieldColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(DataManager.getInstance().getTypeNames().get(cellData.getValue().getValue())));

        ArrayList< Map.Entry<String, Class> > parameter = new ArrayList<>(model.getParameters().entrySet());
        parametersTable.setItems(FXCollections.observableArrayList(parameter));
    }

    private void setupOcurrencesList() {

        occurrencesList.setPlaceholder(new Label("Nenhuma ocorrência registrada"));

        List<String> occurrences = model.getOccurrenceTypes().stream()
                .map(type -> type.getTitle())
                .collect(Collectors.toList());

        occurrencesList.setItems(FXCollections.observableArrayList(new ArrayList<>(occurrences)));
    }
}