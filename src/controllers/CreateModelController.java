package controllers;

import database.ModelDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import models.managers.DataManager;
import models.resource.*;
import util.TableUpdater;

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
    private TableUpdater tableUpdater;

    public void initialize(TableUpdater tableUpdater) {
        
        this.tableUpdater = tableUpdater;

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
        if (new ModelDAO().getAll().stream().anyMatch(m -> m.getName().equals(nameField.getText())))
            return;
        String name = nameField.getText();
        List<Pair<String, Class>> parameters = new ArrayList<>();
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
            if (parameters.stream().anyMatch(p -> p.getKey().equals(box.getTextField().getText()))) {
                System.out.println("Error: field already specified");
                return;
            }
            String paramName = box.getTextField().getText().toLowerCase().replace(' ', '_');
            Class paramClass = DataManager.getInstance().
                    getKeyByValue(box.getTypeBox().getSelectionModel().getSelectedItem());
            parameters.add(new Pair<>(paramName, paramClass));
        }
        for (LineBox lb : typeBoxes) {
            OccurrenceTypeBox box = (OccurrenceTypeBox) lb;
            if (box.getTextField().getText().isEmpty()) {
                System.out.println("Error: no field name specified");
                return;
            }
            if (parameters.stream().anyMatch(p -> p.getKey().equals(box.getTextField().getText()))) {
                System.out.println("Error: field already specified");
                return;
            }
            String typeName = box.getTextField().getText();
            OccurrenceType type = new OccurrenceType(typeName);
            occurrenceTypes.add(type);
        }
        Model model = new Model(name, parameters, occurrenceTypes);

        ModelDAO dao = new ModelDAO();
        dao.add(model);

        tableUpdater.updateTable();
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
