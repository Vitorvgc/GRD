package controllers;

import database.OccurrenceDAO;
import database.OccurrenceTypeDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.resource.Occurrence;
import models.resource.OccurrenceType;
import models.resource.Resource;
import util.StringFormatter;
import util.TableUpdater;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddOccurrenceController {

    @FXML
    private Label resourceNameLabel;
    @FXML
    private Label resourceModelLabel;

    @FXML
    private TextField dayField;
    @FXML
    private TextField monthField;
    @FXML
    private TextField yearField;

    @FXML
    private ScrollPane occurrenceTypePane;
    @FXML
    private VBox occurrenceTypeVBox;

    @FXML
    private TextArea detailsField;

    private Resource resource;
    private OccurrenceType occurrenceType;

    private ToggleGroup group = new ToggleGroup();
    private TableUpdater tableUpdater;

    public void init(Resource resource, TableUpdater tableUpdater) {

        this.resource = resource;
        this.tableUpdater = tableUpdater;

        String modelName = StringFormatter.userFormat(resource.getModel().getName());
        String resourceName = StringFormatter.userFormat(resource.getName());
        this.resourceModelLabel.setText(modelName);
        this.resourceModelLabel.setText(resourceName);

        List<OccurrenceType> occurrences = new OccurrenceTypeDAO().getByModelName(modelName);
        for (OccurrenceType type : occurrences) {
            RadioButton button = new RadioButton(StringFormatter.userFormat(type.getTitle()));
            button.setUserData(StringFormatter.codeFormat(type.getTitle()));
            button.setTextFill(Color.valueOf("#D5D5D5"));
            button.setToggleGroup(group);
            occurrenceTypeVBox.getChildren().add(button);
        }
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (group.getSelectedToggle() != null)
                occurrenceType = new OccurrenceType(group.getSelectedToggle().getUserData().toString());
        });
        this.occurrenceTypePane.setContent(this.occurrenceTypeVBox);

        setupFields();
    }

    @FXML
    public void didPressOkButton() {

        if (dayField.getText().isEmpty()) {
            System.out.println("Error: no field day specified");
            return;
        }
        if (monthField.getText().isEmpty()) {
            System.out.println("Error: no field month specified");
            return;
        }
        if (yearField.getText().isEmpty()) {
            System.out.println("Error: no field year specified");
            return;
        }
        if (detailsField.getText().isEmpty()) {
            System.out.println("Error: no field details specified");
            return;
        }
        if (group.getSelectedToggle() == null) {
            System.out.println("Error: no occurrence type specified");
            return;
        }
        String occurrenceName = group.getSelectedToggle().getUserData().toString();
        OccurrenceType type = new OccurrenceType(occurrenceName);

        String day = dayField.getText();
        String month = monthField.getText();
        String year = yearField.getText();

        String date_s = year + "-" + month + "-" + day;
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = dt.parse(date_s);
        } catch (ParseException e) {
            System.out.println("Error: wrong date format specified");
            return;
        }

        String details = detailsField.getText();

        Occurrence occurrence = new Occurrence(type, date, details);
        OccurrenceDAO dao = new OccurrenceDAO();
        dao.add(occurrence, this.resource);
        tableUpdater.updateTable();
        autoClose();
    }

    @FXML
    public void didPressCancelButton() { autoClose(); }

    private void setupFields() {

        dayField.textProperty().addListener((obs, oldV, newV) -> checkText(dayField, newV));
        monthField.textProperty().addListener((obs, oldV, newV) -> checkText(monthField, newV));
        yearField.textProperty().addListener((obs, oldV, newV) -> checkText(yearField, newV));
    }

    private void checkText(TextField field, String value) {
        if (!value.matches("\\d*")) field.setText(value.replaceAll("[^\\d]", ""));
    }

    private void autoClose() { dayField.getScene().getWindow().hide(); }
}
