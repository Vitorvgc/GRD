package controllers;

import database.GuidelineDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import models.guideline.Guideline;
import models.guideline.GuidelineType;
import util.TableUpdater;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CreateGuidelineController {

    @FXML
    private TextField titleField;

    @FXML
    private ComboBox<GuidelineType> typeComboBox;

    @FXML
    private Label previewNameLabel;
    @FXML
    private Label fileNameLabel;

    private TableUpdater tableUpdater;

    private File file, preview;

    private String contentExtension, previewExtension;

    public void init(TableUpdater tableUpdater) {

        this.tableUpdater = tableUpdater;
        setupComboBox();
    }

    private void setupComboBox() {

        for (GuidelineType type : GuidelineType.values())
            typeComboBox.getItems().add(type);
    }

    @FXML
    private void onImageButtonClicked() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha uma imagem de preview");
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG", "*.jpg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG", "*.png");
        fileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter);
        fileChooser.setSelectedExtensionFilter(pngFilter);

        preview = fileChooser.showOpenDialog(titleField.getScene().getWindow());
        if (preview != null) {
            previewNameLabel.setText(preview.getName());
            int i = preview.getName().lastIndexOf('.');
            if (i > 0) previewExtension = preview.getName().substring(i+1);
        }  else previewNameLabel.setText("Nenhuma imagem selecionada");
    }

    @FXML
    private void onFileButtonClicked() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha um documento ou imagem");
        FileChooser.ExtensionFilter pdfFilter = new FileChooser.ExtensionFilter("PDF", "*.pdf");
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG", "*.jpg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG", "*.png");
        fileChooser.getExtensionFilters().addAll(pdfFilter, jpgFilter, pngFilter);
        fileChooser.setSelectedExtensionFilter(pngFilter);

        file = fileChooser.showOpenDialog(titleField.getScene().getWindow());
        if (file != null) {
            fileNameLabel.setText(file.getName());
            int i = file.getName().lastIndexOf('.');
            if (i > 0) contentExtension = file.getName().substring(i+1);
        } else fileNameLabel.setText("Nenhum arquivo selecionado");
    }

    @FXML
    private void onCreateGuidelineClicked() {

        if (titleField.getText().isEmpty())
            return;
        if (typeComboBox.getValue() == null)
            return;
        if (file == null)
            return;
        if (preview == null)
            preview = new File("./src/images/mpr/defaultPreview.png");

        GuidelineDAO dao = new GuidelineDAO();
        Guideline guideline = new Guideline(titleField.getText(), file, preview, typeComboBox.getValue());
        dao.add(guideline, contentExtension, previewExtension);
        tableUpdater.updateTable();
        onCancelClicked();
    }

    @FXML
    private void onCancelClicked() { titleField.getScene().getWindow().hide(); }
}