package controllers;

import database.OccurrenceDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
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
        setupOccurrencesList();
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

            //TODO: remove resource
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

    private void setupOccurrencesList() {

        Label placeholder = new Label("Nenhuma ocorrência registrada");
        placeholder.setTextFill(Paint.valueOf("#D5D5D5"));
        occurrencesList.setPlaceholder(placeholder);

        List<String> occurrences = new OccurrenceDAO().getAllFrom(resource).stream()
                .map(occurrence -> occurrence.getType().getTitle())
                .collect(Collectors.toList());

        occurrencesList.setItems(FXCollections.observableArrayList(occurrences));
    }
}
