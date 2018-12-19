package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.guideline.Guideline;
import models.guideline.GuidelineType;
import java.io.FileNotFoundException;

public class GuidelineController {

    @FXML
    private ImageView imageView;

    @FXML
    private Label title;

    @FXML
    private Label tag;

    public void init(Guideline guideline) throws FileNotFoundException {

        Image image = new Image(guideline.getPreviewStream());
        this.imageView.setImage(image);
        this.title.setText(guideline.getTitle());
        this.tag.setText(" " + guideline.getType().toString() + " ");
        if (guideline.getType() == GuidelineType.PREVENTION)
            this.tag.setStyle("-fx-background-color: #00a2ff; -fx-background-radius: 5;");
    }
}
