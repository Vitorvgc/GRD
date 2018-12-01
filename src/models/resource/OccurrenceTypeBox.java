package models.resource;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class OccurrenceTypeBox extends LineBox {

    public OccurrenceTypeBox(Pane parent) {
        super(15);
        setAlignment(Pos.CENTER_LEFT);

        Label type = new Label("Tipo");
        type.setTextFill(Color.web("#d5d5d5"));
        field.setPrefWidth(110);

        getChildren().addAll(type, field, removeParameterButton);
    }

    public void setEditable(boolean editable) {

        field.setDisable(!editable);
        field.setOpacity(1.0);
        field.setStyle("-fx-text-fill: " + (editable ? "black" : "gray"));

        removeParameterButton.setVisible(editable);
    }

    public Button getRemoveButton() { return removeParameterButton; }

    public TextField getTextField() { return field; }

    public void setTextFieldName(String name) {
        this.field.setText(name);
    }
}
