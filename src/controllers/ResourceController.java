package controllers;

import database.ResourceDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.resource.Resource;
import util.AlertHelper;
import util.StringFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class ResourceController {

    @FXML
    private Label resourceNameLabel,
                  resourceModelLabel;

    @FXML
    private ToggleGroup contentTabs;

    @FXML
    private ToggleButton informationToggleButton,
                         occurrencesToggleButton;

    @FXML
    private ListView<String> occurrencesList;

    @FXML
    private TableView<Pair<String, Object>> informationTable;

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

        Alert alert = AlertHelper.warning("Excluir Recurso",
                "Tem certeza que deseja excluir o recurso?\nEssa ação é irreversível.", "Sim", "Não");
        Optional<ButtonType> option = alert.showAndWait();

        if(option.isPresent() && option.get().getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            new ResourceDAO().delete(resource);
            Stage stage = (Stage) contentContainer.getScene().getWindow();
            stage.close();
        }
    }

    private void setupTitle() {
        resourceNameLabel.setText(StringFormatter.userFormat(resource.getName()));
        resourceModelLabel.setText(StringFormatter.userFormat(resource.getModel().getName()));
    }

    private void setupToggleButtons() {

        contentTabs.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue == null)
                oldValue.setSelected(true);
            else if(informationToggleButton.isSelected()) {
                informationTable.setVisible(true);
                occurrencesList.setVisible(false);
            }
            else if(occurrencesToggleButton.isSelected()) {
                informationTable.setVisible(false);
                occurrencesList.setVisible(true);
            }
        });
    }

    private void setupInformationTable() {

        TableColumn<Pair<String, Object>, String> fieldColumn = (TableColumn) informationTable.getColumns().get(0);
        TableColumn<Pair<String, Object>, String> valueColumn = (TableColumn) informationTable.getColumns().get(1);

        fieldColumn.setCellValueFactory(cellData -> new SimpleStringProperty(StringFormatter.userFormat(cellData.getValue().getKey())));
        valueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));

        ArrayList<Pair<String, Object>> information = new ArrayList<>(resource.getData());
        informationTable.setItems(FXCollections.observableArrayList(information));
    }

    private void setupOcurrencesList() {

        occurrencesList.setPlaceholder(new Label("Nenhuma ocorrência registrada"));

        List<String> occurrences = resource.getOccurrences().stream()
                .map(occurrence -> occurrence.getType().getTitle())
                .collect(Collectors.toList());

        occurrencesList.setItems(FXCollections.observableArrayList(new ArrayList<>(occurrences)));
    }
}
