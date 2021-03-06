package controllers;

import database.ModelDAO;
import database.ResourceDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import models.resource.Model;
import models.resource.Resource;
import util.StringFormatter;
import util.TableUpdater;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateResourceController {

    @FXML
    private ScrollPane parametersPane;

    private ComboBox<String> modelBox = new ComboBox<>();
    HBox headerHB = new HBox(15);
    VBox contentVB = new VBox(20);
    private Resource resource = null;
    private Model selectedModel = null;
    private Map<String, Object> data;
    private TableUpdater tableUpdater;

    @FXML
    private void initialize() {

        ObservableList<Model> models = FXCollections.observableArrayList(new ModelDAO().getAll());

        for (Model model : models)
            modelBox.getItems().add(StringFormatter.userFormat(model.getName()));
        modelBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            for (Model model : models)
                if (StringFormatter.userFormat(model.getName()).equals(newValue)) {
                    selectedModel = model;
                    data = new HashMap<>();
                    break;
                }
            updateParametersPane(selectedModel);
        });
        VBox vb = new VBox(20);
        vb.setPadding(new Insets(10, 10, 10, 10));

        Label label = new Label("Escolha um modelo");
        label.setTextFill(Color.web("#d5d5d5"));
        modelBox.setPrefWidth(170);
        headerHB.setAlignment(Pos.CENTER_LEFT);
        headerHB.getChildren().addAll(label, modelBox);
        vb.getChildren().addAll(headerHB, contentVB);
        parametersPane.setContent(vb);
    }

    private void updateParametersPane(Model model) {

        contentVB.getChildren().clear();

        model.getParameters().stream().map(Pair::getKey).forEach(paramName -> {
            data.put(paramName, null);
            HBox hb = new HBox(15);
            hb.setAlignment(Pos.CENTER_LEFT);
            Label name = new Label(StringFormatter.userFormat(paramName));
            name.setTextFill(Color.web("#d5d5d5"));
            name.setPrefWidth(120);
            TextField field = new TextField();
            field.setPrefWidth(170);
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                newValue = newValue.trim();
                if (!newValue.isEmpty())
                    data.put(paramName, newValue);
                else
                    data.put(paramName, null);
            });
            hb.getChildren().addAll(name, field);
            contentVB.getChildren().add(hb);
        });
    }

    @FXML
    private void onCreateResourceClicked() {

        if (selectedModel == null || data == null)
            return;

        ArrayList<Pair<String, Object>> dataArray = new ArrayList<>();

        selectedModel.getParameters().stream().map(Pair::getKey).forEach(param ->
            dataArray.add(new Pair<>(param, data.get(param)))
        );

        resource = new Resource(selectedModel, dataArray);

        if (resource.getData().stream().anyMatch(param -> param.getValue() == null))
            return;

        new ResourceDAO().add(resource);
        if (tableUpdater != null)
            tableUpdater.updateTable();
        onCancelClicked();
    }

    @FXML
    private void onCancelClicked() {
        parametersPane.getScene().getWindow().hide();
    }

    public void setTableUpdater(TableUpdater tableUpdater) {
        this.tableUpdater = tableUpdater;
    }
}
