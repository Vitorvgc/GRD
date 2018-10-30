package models.guideline;

public class Guideline {

    private String name, content;
    private GuidelineType type;

    public Guideline(String name, String content, GuidelineType type) {
        this.name = name;
        this.content = content;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public GuidelineType getType() {
        return type;
    }
}
