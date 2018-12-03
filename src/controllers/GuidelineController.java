package controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.guideline.GuidelineType;

public class GuidelineController {

    @FXML
    private ImageView imageView;

    @FXML
    private Text title;

    @FXML
    private Label tag;

    public void init(Image image, String text, GuidelineType tag) {
        this.imageView.setImage(image);
        this.title.setText(text);
        switch (tag) {
            case EMERGENCY:
                this.tag.setText(" Emergência ");
                break;
            case PREVENTION:
                this.tag.setText(" Prevenção ");
                this.tag.setStyle("-fx-background-color: #00a2ff; -fx-background-radius: 5;");
                break;
            default:
        }
    }
}
