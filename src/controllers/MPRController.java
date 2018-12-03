package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import models.guideline.GuidelineType;

import java.io.IOException;

public class MPRController {

    @FXML
    private GridPane grid;

    @FXML
    private void initialize() {
        String[][] titles = new String[3][2];
        Image[][] images = new Image[3][2];
        GuidelineType[][] tags = new GuidelineType[3][2];

        titles[0][0] = "Como utilizar corretamente os extintores de …";
        titles[0][1] = "O que fazer em caso de acidente por queimadu…";
        titles[1][0] = "Como evitar acidentes durante o trabalho";
        titles[1][1] = "Como utilizar corretamente os extintores de …";
        titles[2][0] = "O que fazer em caso de acidente por queimadu…";

        images[0][0] = new Image(getClass().getResource("../images/mpr/1.png").toExternalForm());
        images[0][1] = new Image(getClass().getResource("../images/mpr/2.png").toExternalForm());
        images[1][0] = new Image(getClass().getResource("../images/mpr/3.png").toExternalForm());
        images[1][1] = new Image(getClass().getResource("../images/mpr/4.png").toExternalForm());
        images[2][0] = new Image(getClass().getResource("../images/mpr/5.png").toExternalForm());

        tags[0][0] = GuidelineType.EMERGENCY;
        tags[0][1] = GuidelineType.PREVENTION;
        tags[1][0] = GuidelineType.EMERGENCY;
        tags[1][1] = GuidelineType.EMERGENCY;
        tags[2][0] = GuidelineType.PREVENTION;

        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 2; ++j) {
                if (i == 2 && j == 1) break;

                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/guideline.fxml"));
                GuidelineController controller = new GuidelineController();
                loader.setController(controller);

                try {
                    grid.add(loader.load(), j, i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                controller.init(images[i][j], titles[i][j], tags[i][j]);
            }
    }
}
