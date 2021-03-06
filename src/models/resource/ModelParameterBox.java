package models.resource;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import util.TypeName;

import java.util.Arrays;

public class ModelParameterBox extends LineBox {

    private ComboBox<String> typeBox = new ComboBox<>();
    private boolean editable = true;

    public ModelParameterBox() {

        super(15);

        setAlignment(Pos.CENTER_LEFT);

        Label name = new Label("Nome");
        name.setTextFill(Color.web("#d5d5d5"));

        field.setPrefWidth(110);

        Label type = new Label("Tipo");
        type.setTextFill(Color.web("#d5d5d5"));

        Arrays.stream(TypeName.values()).map(TypeName::toString).forEach(typeName ->
                typeBox.getItems().add(typeName)
        );

        getChildren().addAll(name, field, type, typeBox, removeParameterButton);
    }

    public ModelParameterBox(boolean editable) {

        this();
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

        this.typeBox.getItems().add(value);
        this.typeBox.getSelectionModel().select(value);
    }
}
