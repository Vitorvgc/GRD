package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.resource.Model;
import models.resource.Ocurrence;
import models.resource.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourcesController {

    @FXML
    private TableView<Resource> resourcesTable;

    private ObservableList<Resource> data = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        setupSampleData();
        setupTable();
    }

    @FXML
    private void addResource() {
        System.out.println("Add resource");
    }


    // Hardcoded data for test purposes.
    private void setupSampleData() {

        Map<String, Class> params = new HashMap<>();
        params.put("Name", String.class);
        params.put("Age", int.class);

        List<Ocurrence> ocurrences = new ArrayList<>();
        ocurrences.add(new Ocurrence("Dead"));

        Model model = new Model("User", params, ocurrences);

        Map<String, Object> values = new HashMap<>();
        values.put("Name", "user");
        values.put("Age", 21);

        data.add(new Resource("User 1", model, 1, values));
        data.add(new Resource("User 2", model, 1, values));
        data.add(new Resource("User 3", model, 1, values));
    }

    private void setupTable() {

        TableColumn<Resource, String> nameColumn = (TableColumn) resourcesTable.getColumns().get(0);
        TableColumn<Resource, String> modelColumn = (TableColumn) resourcesTable.getColumns().get(1);
        TableColumn<Resource, String> sectionColumn = (TableColumn) resourcesTable.getColumns().get(2);
        TableColumn<Resource, String> ocurrencesColumn = (TableColumn) resourcesTable.getColumns().get(3);

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        modelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModel().getName()));
        sectionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getSection())));
        ocurrencesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getOcurrences().size())));

        resourcesTable.setItems(data);
        resourcesTable.setPlaceholder(new Label("No resources registered"));
    }
}
