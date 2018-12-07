package controllers;

import database.ModelDAO;
import database.OccurrenceTypeDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import models.resource.*;
import util.StringFormatter;
import util.TableUpdater;
import util.TypeName;
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

    private List<LineBox> paramBoxes = new ArrayList<>();
    private List<LineBox> typeBoxes = new ArrayList<>();
    private TableUpdater tableUpdater;

    public void init(TableUpdater tableUpdater) {
        this.tableUpdater = tableUpdater;

        ModelParameterBox nameField = new ModelParameterBox(false);
        nameField.setTextFieldName("Nome");
        nameField.setTypeBoxValue("texto");
        setupNewLineBox(nameField, paramBoxes, parametersVB);

        ModelParameterBox sectorField = new ModelParameterBox(false);
        sectorField.setTextFieldName("Setor");
        sectorField.setTypeBoxValue("texto");
        setupNewLineBox(sectorField, paramBoxes, parametersVB);
    }

    @FXML
    private void onAddParameterClicked() {
        ModelParameterBox hb = new ModelParameterBox();
        setupNewLineBox(hb, paramBoxes, parametersVB);
    }

    @FXML
    private void onAddOccurrenceClicked() {
        OccurrenceTypeBox hb = new OccurrenceTypeBox(occurrencesVB);
        setupNewLineBox(hb, typeBoxes, occurrencesVB);
    }

    @FXML
    private void onCreateModelClicked() {
        String name = nameField.getText().trim();

        if (name.isEmpty())
            return;
        if (new ModelDAO().getAll().stream().anyMatch(m -> m.getName().equals(name)))
            return;

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
            String paramName = StringFormatter.codeFormat(box.getTextField().getText());
            String selectedClass = box.getTypeBox().getSelectionModel().getSelectedItem();
            Class paramClass = TypeName.fromUserType(selectedClass).toJavaClass();
            parameters.add(new Pair<>(paramName, paramClass));
        }
        for (LineBox lb : typeBoxes) {
            OccurrenceTypeBox box = (OccurrenceTypeBox) lb;
            String typeName = box.getTextField().getText().trim();

            if (typeName.isEmpty()) {
                System.out.println("Error: no field name specified");
                return;
            }
            if (parameters.stream().anyMatch(p -> p.getKey().equals(typeName))) {
                System.out.println("Error: field already specified");
                return;
            }
            OccurrenceType type = new OccurrenceType(typeName);
            occurrenceTypes.add(type);
        }
        Model model = new Model(name, parameters, occurrenceTypes);

        ModelDAO modelDAO = new ModelDAO();
        modelDAO.add(model);

        OccurrenceTypeDAO occurrenceTypeDAO = new OccurrenceTypeDAO();
        for (OccurrenceType occurrenceType : occurrenceTypes)
            occurrenceTypeDAO.add(occurrenceType, model);

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
