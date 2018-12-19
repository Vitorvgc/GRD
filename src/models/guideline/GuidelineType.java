package models.guideline;

public enum GuidelineType {

    EMERGENCY, PREVENTION;

    @Override
    public String toString() {

        switch (this) {
            case EMERGENCY:  return "Emergência";
            case PREVENTION: return "Prevenção";
            default:         return null;
        }
    }

    public static GuidelineType fromSQLDataType(String type) {

        type = type.toLowerCase();

        switch (type) {
            case "emergência": return EMERGENCY;
            case "prevenção":  return PREVENTION;
            default:           return null;
        }
    }
}
