package controllers;

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
import models.managers.DataManager;
import models.resource.Model;
import models.resource.Resource;
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

    public void init() {
        for (Model model : DataManager.getInstance().getModels())
            modelBox.getItems().add(model.getName());
        modelBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            for (Model model : DataManager.getInstance().getModels())
                if (model.getName().compareTo(newValue) == 0) {
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
        for (String paramName : model.getParameters().keySet()) {
            data.put(paramName, null);
            HBox hb = new HBox(15);
            hb.setAlignment(Pos.CENTER_LEFT);
            Label name = new Label(paramName);
            name.setTextFill(Color.web("#d5d5d5"));
            name.setPrefWidth(120);
            TextField field = new TextField();
            field.setPrefWidth(170);
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.compareTo("") != 0)
                    data.put(paramName, newValue);
                else
                    data.put(paramName, null);
            });
            hb.getChildren().addAll(name, field);
            contentVB.getChildren().add(hb);
        }
    }

    @FXML
    private void onCreateResourceClicked() {

        if(selectedModel == null || data == null)
            return;

        resource = new Resource(selectedModel, data);

        for (String param : resource.getData().keySet())
            if (resource.getData().get(param) == null)
                return;
        DataManager.getInstance().getResources().add(resource);
        onCancelClicked();
    }

    @FXML
    private void onCancelClicked() {
        parametersPane.getScene().getWindow().hide();
    }
}
