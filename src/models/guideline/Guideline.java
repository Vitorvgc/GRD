package models.guideline;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Guideline {

    private String title;
    private File content, preview;
    private GuidelineType type;

    public Guideline(String title, File content, File preview, GuidelineType type) {

        this.title = title;
        this.content = content;
        this.preview = preview;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public File getContent() {
        return content;
    }

    public GuidelineType getType() {
        return type;
    }

    public FileInputStream getContentStream() throws FileNotFoundException {
        return new FileInputStream(content);
    }

    public FileInputStream getPreviewStream() throws FileNotFoundException {
        return new FileInputStream(preview);
    }
}
