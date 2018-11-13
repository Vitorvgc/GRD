package models.resource;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import models.managers.DataManager;

public class ParameterModelBox extends HBox {

    private Button removeParameterButton = new Button("-");
    private TextField field = new TextField();
    private ComboBox<String> typeBox = new ComboBox<>();

    public ParameterModelBox(Pane parent) {
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

    public Button getRemoveButton() { return removeParameterButton; }

    public TextField getTextField() { return field; }

    public ComboBox<String> getTypeBox() { return typeBox; }
}
