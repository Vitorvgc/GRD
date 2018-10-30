package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class MainController {

    @FXML
    private Pane
            resourceButton,
            modelButton,
            mprButton,
            reportButton,
            registerButton,
            exitButton;

    @FXML
    private Label
            resourceLabel,
            modelLabel,
            mprLabel,
            reportLabel,
            registerLabel,
            exitLabel;

    @FXML
    private ImageView
            resourceIcon,
            modelIcon,
            mprIcon,
            reportIcon,
            registerIcon,
            exitIcon;

    @FXML
    private AnchorPane contentPane;

    private Pane[] buttons;
    private Label[] labels;
    private ImageView[] icons;
    private String[] names;

    public void init() {
        buttons = new Pane[6];
        buttons[0] = resourceButton;
        buttons[1] = modelButton;
        buttons[2] = mprButton;
        buttons[3] = reportButton;
        buttons[4] = registerButton;
        buttons[5] = exitButton;

        labels = new Label[6];
        labels[0] = resourceLabel;
        labels[1] = modelLabel;
        labels[2] = mprLabel;
        labels[3] = reportLabel;
        labels[4] = registerLabel;
        labels[5] = exitLabel;

        icons = new ImageView[6];
        icons[0] = resourceIcon;
        icons[1] = modelIcon;
        icons[2] = mprIcon;
        icons[3] = reportIcon;
        icons[4] = registerIcon;
        icons[5] = exitIcon;

        names = new String[6];
        names[0] = "../images/icons/resources";
        names[1] = "../images/icons/models";
        names[2] = "../images/icons/mpr";
        names[3] = "../images/icons/reports";
        names[4] = "../images/icons/register";
        names[5] = "../images/icons/exit";

        onResourceTabClicked();
    }

    @FXML
    private void onResourceTabClicked() {
        setButtonsToNormalState();
        highlight(resourceButton, resourceLabel, resourceIcon, names[0]);
    }

    @FXML
    private void onModelTabClicked() {
        setButtonsToNormalState();
        highlight(modelButton, modelLabel, modelIcon, names[1]);
    }

    @FXML
    private void onMPRTabClicked() {
        setButtonsToNormalState();
        highlight(mprButton, mprLabel, mprIcon, names[2]);
    }

    @FXML
    private void onReportTabClicked() {
        setButtonsToNormalState();
        highlight(reportButton, reportLabel, reportIcon, names[3]);
    }

    @FXML
    private void onRegisterTabClicked() {
        setButtonsToNormalState();
        highlight(registerButton, registerLabel, registerIcon, names[4]);
    }

    @FXML
    private void onExitTabClicked() {
        setButtonsToNormalState();
        highlight(exitButton, exitLabel, exitIcon, names[5]);
    }

    private void highlight(Pane button, Label text, ImageView icon, String iconName) {

        button.setStyle("-fx-background-color: #42C3FF");
        text.setTextFill(Color.web("#3D4B5E"));
        Image image = new Image(getClass().getResource(iconName + "_dark.png").toExternalForm());
        icon.setImage(image);
    }

    private void setButtonsToNormalState() {
        for (int i = 0; i < buttons.length; ++i) {
            buttons[i].setStyle("-fx-background-color: #3D4B5E");
            labels[i].setTextFill(Color.web("#D5D5D5"));
            icons[i].setImage(new Image(getClass().getResource(names[i] + "_light.png").toExternalForm()));
        }
    }
}
