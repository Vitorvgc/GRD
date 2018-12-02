package util;

public enum TypeName {
    TEXT,
    NUMBER;

    public static TypeName fromUserType(String type) {

        type = type.toLowerCase();

        switch (type) {
            case "texto":  return TEXT;
            case "número": return NUMBER;
            default:       return null;
        }
    }

    public static TypeName fromJavaClass(Class type) {

        if (type == String.class)
            return TEXT;
        if (type == int.class)
            return NUMBER;

        return null;
    }

    public static TypeName fromSQLDataType(String type) {

        type = type.toLowerCase();

        switch (type) {
            case "varchar": return TEXT;
            case "int":     return NUMBER;
            default:        return null;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case TEXT:   return "texto";
            case NUMBER: return "número";
            default:     return null;
        }
    }

    public Class toJavaClass() {
        switch (this) {
            case TEXT:   return String.class;
            case NUMBER: return int.class;
            default:     return null;
        }
    }

    public String toSQLDataType() {
        switch (this) {
            case TEXT:   return "varchar(80)";
            case NUMBER: return "int";
            default:     return null;
        }
    }
}
