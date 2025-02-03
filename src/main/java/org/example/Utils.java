package org.example;

import org.example.model.LinkedList;

public class Utils {
    public static <T> String convertLinkedListToHumanReadableString(LinkedList<T> list) {
        StringBuilder output = new StringBuilder();
        for (T item : list) {
            output.append(item).append("\n");
        }
        return output.toString();
    }
}
