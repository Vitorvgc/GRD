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
    private void initialize() {
        ParameterModelBox nameField = new ParameterModelBox(contentVB, false);
        nameField.setTextFieldName("Nome");
        nameField.setTypeBoxValue("Texto");
        setupNewParameterBox(nameField);

        ParameterModelBox sectorField = new ParameterModelBox(contentVB, false);
        sectorField.setTextFieldName("Setor");
        sectorField.setTypeBoxValue("Texto");
        setupNewParameterBox(sectorField);
    }

    @FXML
    private void onAddParameterClicked() {
        ParameterModelBox hb = new ParameterModelBox(contentVB);
        setupNewParameterBox(hb);
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
        for (ParameterModelBox box : boxes) {
            if (box.getTextField().getText().isEmpty()) {
                System.out.println("Error: no field name specified");
                return;
            }
            if (box.getTypeBox().getSelectionModel().isEmpty()) {
                System.out.println("Error: no field type specified");
                return;
            }
            if (parameters.containsKey(box.getTextField().getText())) {
                System.out.println("Error: field already specified");
                return;
            }
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

    private void setupNewParameterBox(ParameterModelBox box) {
        box.getRemoveButton().setOnAction(event -> {
            contentVB.getChildren().remove(box);
            boxes.remove(box);
        });
        contentVB.getChildren().add(box);
        boxes.add(box);
    }

    @FXML
    private void onCancelClicked() {
        parametersPane.getScene().getWindow().hide();
    }
}
