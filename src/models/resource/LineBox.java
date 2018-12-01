package models.resource;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public abstract class LineBox extends HBox {

    protected Button removeParameterButton = new Button("-");
    protected TextField field = new TextField();

    public LineBox(double spacing) {
        super(spacing);
    }

    public abstract Button getRemoveButton();
}
