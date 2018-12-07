package util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StringFormatter {

    private static Set<String> notCapitalizedWords = new HashSet<>(
            Arrays.asList("a", "e", "o", "da", "de", "do")
    );

    public static String userFormat(String string) {

        string = string.replace('_', ' ');
        string = string.replaceAll("\\s+", " ");
        return capitalized(string.trim());
    }

    public static String codeFormat(String string) {

        return string.toLowerCase().trim().replaceAll("\\s+", "_");
    }

    public static String capitalized(String string) {

        String[] words = string.toLowerCase().split(" ");

        StringBuilder sb = new StringBuilder();

        for (String word : words) {
            if (notCapitalizedWords.contains(word))
                sb.append(word);
            else
                sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
            sb.append(" ");
        }

        return sb.toString().trim();
    }
}
