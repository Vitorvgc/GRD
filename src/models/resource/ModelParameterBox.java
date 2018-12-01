package models.resource;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import models.managers.DataManager;

public class ModelParameterBox extends LineBox {

    private ComboBox<String> typeBox = new ComboBox<>();
    private boolean editable = true;

    public ModelParameterBox(Pane parent) {
        super(15);
        setAlignment(Pos.CENTER_LEFT);

        Label name = new Label("Nome");
        name.setTextFill(Color.web("#d5d5d5"));

        field.setPrefWidth(110);

        Label type = new Label("Tipo");
        name.setTextFill(Color.web("#d5d5d5"));

        for (String typeName : DataManager.getInstance().getTypeNames().values())
            typeBox.getItems().add(typeName);

        getChildren().addAll(name, field, type, typeBox, removeParameterButton);
    }

    public ModelParameterBox(Pane parent, boolean editable) {
        this(parent);
        this.setEditable(editable);
    }

    public void setEditable(boolean editable) {

        if(editable == this.editable) return;

        this.editable = editable;
        field.setDisable(!editable);
        field.setOpacity(1.0);
        field.setStyle("-fx-text-fill: " + (editable ? "black" : "gray"));
        typeBox.setDisable(!editable);
        typeBox.setOpacity(1.0);

        removeParameterButton.setVisible(editable);
    }

    public boolean isEditable() {
        return this.editable;
    }

    public Button getRemoveButton() { return removeParameterButton; }

    public TextField getTextField() { return field; }

    public ComboBox<String> getTypeBox() { return typeBox; }

    public void setTextFieldName(String name) {
        this.field.setText(name);
    }

    public void setTypeBoxValue(String value) {
        this.typeBox.setValue(value);
    }
}
