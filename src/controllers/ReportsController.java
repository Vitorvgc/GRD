package controllers;

import database.ModelDAO;
import database.OccurrenceDAO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.resource.Model;
import util.StringFormatter;

import java.util.*;

public class ReportsController {

    @FXML
    private PieChart pieChart;

    @FXML
    private TableView<PieChart.Data> chartTable;

    @FXML
    private Label totalLabel;

    private ObservableList<PieChart.Data> pieChartData, chartTableData;

    @FXML
    private void initialize() {
        setupData();
        setupPieChart();
        setupPieChartTable();
    }

    private void setupData() {

        pieChartData = FXCollections.observableArrayList();
        chartTableData = FXCollections.observableArrayList();

        int total = 0;
        List<Model> models = new ModelDAO().getAll();
        for (Model model : models) {
            String name = StringFormatter.userFormat(model.getName());
            int numberOccurrences = new OccurrenceDAO().countFrom(model);
            total += numberOccurrences;
            PieChart.Data data = new PieChart.Data(name, numberOccurrences);
            chartTableData.add(data);
            if (numberOccurrences > 0)
                pieChartData.add(data);
        }

        chartTableData.sort(
            (o1, o2) -> {
                if (o1.getPieValue() != o2.getPieValue())
                    return o1.getPieValue() > o2.getPieValue() ? -1 : 1;
                return o1.getName().compareTo(o2.getName());
            }
        );

        totalLabel.setText("Total: " + total);
    }

    private void setupPieChart() {

        pieChart.setTitle("Ocorrências por modelo");
        pieChart.setData(pieChartData);
        pieChart.setLabelsVisible(false);
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart.setStartAngle(90);
    }

    private void setupPieChartTable() {

        TableColumn<PieChart.Data, String> modelColumn = (TableColumn) chartTable.getColumns().get(0);
        TableColumn<PieChart.Data, Integer> quantityColumn = (TableColumn) chartTable.getColumns().get(1);

        modelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        quantityColumn.setCellValueFactory(cellData -> {
            int value = (int) cellData.getValue().getPieValue();
            return new SimpleIntegerProperty(value).asObject();
        });

        chartTable.setItems(chartTableData);
    }
}
