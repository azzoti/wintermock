package org.lazyluke.wintermock;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class CucumberSupport {

    public static final String PARAMETER = "PARAMETER";
    public static final String RETURNING = "RETURNING";


    public static Map<String, String> getExpressionMapFromTable(List<List<String>> table, String requiredDirectionRegex, String requiredParameterNameRegex, boolean canBeEmpty) {
        assert requiredDirectionRegex.equals(PARAMETER) || requiredDirectionRegex.equals(RETURNING);
        Map<String, String> result = new LinkedHashMap<>();
        for (List<String> row : table) {
            //List<String> cells = row.getCells();
            assert row.size() == 4;
            String tableDirection = row.get(0);
            assert tableDirection.equals(PARAMETER) || tableDirection.equals(RETURNING);
            String tableParameterName = row.get(1);
            String fieldExpression = row.get(2);
            String fieldValue = row.get(3);
            if (tableDirection.matches(requiredDirectionRegex) && tableParameterName.matches(requiredParameterNameRegex)) {
                result.put(fieldExpression, fieldValue);
            }
        }
        if (!canBeEmpty && result.size() == 0) {
            throw new IllegalStateException("No entries in table for " + requiredDirectionRegex + " " + requiredParameterNameRegex);
        }
        return result;
    }
}
