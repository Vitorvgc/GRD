package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import models.managers.DataManager;
import models.resource.Model;
import models.resource.OccurrenceType;
import models.resource.ParameterModelBox;

import java.util.*;

public class CreateModelController {

    @FXML
    private ScrollPane parametersPane;

    @FXML
    private TextField nameField;

    @FXML
    private VBox contentVB;

    private Model model = null;
    private List<ParameterModelBox> boxes = new ArrayList<>();

    @FXML
    private void onAddParameterClicked() {
        ParameterModelBox hb = new ParameterModelBox(contentVB);
        hb.getRemoveButton().setOnAction(event -> {
            contentVB.getChildren().remove(hb);
            boxes.remove(hb);
        });
        contentVB.getChildren().add(hb);
        boxes.add(hb);
    }

    @FXML
    private void onCreateModelClicked() {
        if (nameField.getText().isEmpty())
            return;
        if (DataManager.getInstance().getModels().stream().filter(
                m -> m.getName().compareTo(nameField.getText()) == 0).count() != 0)
            return;
        String name = nameField.getText();
        Map<String, Class> parameters = new HashMap<>();
        parameters.put("Nome", String.class);
        parameters.put("Setor", int.class);
        for (ParameterModelBox box : boxes) {
            if (box.getTextField().getText().isEmpty())
                return;
            if (box.getTypeBox().getSelectionModel().isEmpty())
                return;
            if (parameters.containsKey(box.getTextField().getText()))
                return;
            String paramName = box.getTextField().getText();
            Class paramClass = DataManager.getInstance().
                    getKeyByValue(box.getTypeBox().getSelectionModel().getSelectedItem());
            parameters.put(paramName, paramClass);
        }
        List<OccurrenceType> occurrences = new ArrayList<>();
        occurrences.add(new OccurrenceType("ocorrencia"));
        Model model = new Model(name, parameters, occurrences);
        DataManager.getInstance().getModels().add(model);
        onCancelClicked();
    }

    @FXML
    private void onCancelClicked() {
        parametersPane.getScene().getWindow().hide();
    }
}
