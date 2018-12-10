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
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class AddOccurrenceController {

    @FXML
    private Label resourceNameLabel;
    @FXML
    private Label resourceModelLabel;

    @FXML
    private ScrollPane occurrenceTypePane;

    @FXML
    private VBox occurrenceTypeVBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea detailsField;

    private Resource resource;
    private ToggleGroup group = new ToggleGroup();
    private TableUpdater tableUpdater;

    public void init(Resource resource, TableUpdater tableUpdater) {

        this.resource = resource;
        this.tableUpdater = tableUpdater;

        String modelName = StringFormatter.userFormat(resource.getModel().getName());
        String resourceName = StringFormatter.userFormat(resource.getName());
        this.resourceModelLabel.setText(modelName);
        this.resourceNameLabel.setText(resourceName);

        List<OccurrenceType> occurrences = new OccurrenceTypeDAO().getByModelName(modelName);
        for (OccurrenceType type : occurrences) {
            RadioButton button = new RadioButton(StringFormatter.userFormat(type.getTitle()));
            button.setUserData(StringFormatter.codeFormat(type.getTitle()));
            button.setTextFill(Color.valueOf("#D5D5D5"));
            button.getStyleClass().add("blue-radio-button");
            button.setToggleGroup(group);
            occurrenceTypeVBox.getChildren().add(button);
        }
        this.occurrenceTypePane.setContent(this.occurrenceTypeVBox);
    }

    @FXML
    public void didPressOkButton() {

        if (datePicker.getValue() == null) {
            System.out.println("Error: no date specified");
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

        LocalDate localDate = datePicker.getValue();
        String date_s = localDate.getYear() + "-" + localDate.getMonthValue() + "-" + localDate.getDayOfMonth();
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

    private void autoClose() { datePicker.getScene().getWindow().hide(); }
}
