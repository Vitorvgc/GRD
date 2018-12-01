package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.managers.DataManager;
import models.resource.*;

import java.util.*;

public class CreateModelController {

    @FXML
    private ScrollPane parametersPane;

    @FXML
    private TextField nameField;

    @FXML
    private VBox contentVB;
    @FXML
    private VBox parametersVB;
    @FXML
    private VBox occurrencesVB;

    private Model model = null;
    private List<LineBox> paramBoxes = new ArrayList<>();
    private List<LineBox> typeBoxes = new ArrayList<>();

    @FXML
    private void initialize() {
        ModelParameterBox nameField = new ModelParameterBox(parametersVB, false);
        nameField.setTextFieldName("Nome");
        nameField.setTypeBoxValue("Texto");
        setupNewLineBox(nameField, paramBoxes, parametersVB);

        ModelParameterBox sectorField = new ModelParameterBox(parametersVB, false);
        sectorField.setTextFieldName("Setor");
        sectorField.setTypeBoxValue("Texto");
        setupNewLineBox(sectorField, paramBoxes, parametersVB);
    }

    @FXML
    private void onAddParameterClicked() {
        ModelParameterBox hb = new ModelParameterBox(parametersVB);
        setupNewLineBox(hb, paramBoxes, parametersVB);
    }

    @FXML
    private void onAddOccurrenceClicked() {
        OccurrenceTypeBox hb = new OccurrenceTypeBox(occurrencesVB);
        setupNewLineBox(hb, typeBoxes, occurrencesVB);
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
        List<OccurrenceType> occurrenceTypes = new ArrayList<>();
        for (LineBox lb : paramBoxes) {
            ModelParameterBox box = (ModelParameterBox) lb;
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
        for (LineBox lb : typeBoxes) {
            OccurrenceTypeBox box = (OccurrenceTypeBox) lb;
            if (box.getTextField().getText().isEmpty()) {
                System.out.println("Error: no field name specified");
                return;
            }
            if (parameters.containsKey(box.getTextField().getText())) {
                System.out.println("Error: field already specified");
                return;
            }
            String typeName = box.getTextField().getText();
            OccurrenceType type = new OccurrenceType(typeName);
            occurrenceTypes.add(type);
        }
        Model model = new Model(name, parameters, occurrenceTypes);
        DataManager.getInstance().getModels().add(model);
        onCancelClicked();
    }

    private void setupNewLineBox(LineBox box, List<LineBox> array, Pane pane) {
        box.getRemoveButton().setOnAction(event -> {
            pane.getChildren().remove(box);
            array.remove(box);
        });
        pane.getChildren().add(box);
        array.add(box);
    }

    @FXML
    private void onCancelClicked() {
        parametersPane.getScene().getWindow().hide();
    }
}