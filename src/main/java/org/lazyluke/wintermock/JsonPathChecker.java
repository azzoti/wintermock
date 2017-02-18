package org.lazyluke.wintermock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import net.minidev.json.JSONArray;

public class JsonPathChecker {

    private static final String NULL_STRING = "null";
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonPathChecker.class);
    public static final String VALUE_KEYWORD = "value";
    public static final String JSON_PATH_ROOT_ELEMENT = "$";
    public static final String JSON_PATH_FOR_ANY_CHILD_FROM_ROOT = "$..";
    public static final String JSON_PATH_FIRST_ELEMENT_OF_ROOT_ARRAY = "$[0]";

    public static boolean verifyContains(String json, final Map<String, String> parameterExpressionMap) {

        json = makeJsonIfPlainString(json);

        Object document = Configuration.defaultConfiguration().addOptions(Option.ALWAYS_RETURN_LIST).jsonProvider().parse(json);

        for (String jsonPathExpression : parameterExpressionMap.keySet()) {
            String modifiedJsonPathExpression = ensureJsonPathExpressionIfNotAbsolutePath(jsonPathExpression);

            Object jsonPathReadResult = JsonPath.read(document, modifiedJsonPathExpression);
            Object matchedElement;
            String expectedValue = parameterExpressionMap.get(jsonPathExpression);

            if (jsonPathReadResult instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) jsonPathReadResult;
                List<Object> nonNullJsonArray = getAsNonNullList(jsonArray);
                if (nonNullJsonArray.size() == 0 && !expectedValue.equals(NULL_STRING)) {
                    LOGGER.info("No match for {} in {}", modifiedJsonPathExpression, json);
                    return false;
                }
                checkMoreThanOneElementAndThrow(json, jsonPathExpression, nonNullJsonArray);
                if(expectedValue.equals(NULL_STRING)){
                    matchedElement = null;
                } else {
                    matchedElement = nonNullJsonArray.get(0);
                }
            } else {
                matchedElement = jsonPathReadResult;
            }

            if (matchedElement == null) {
                if (!expectedValue.equals(NULL_STRING)) {
                    logNoMatch(modifiedJsonPathExpression, matchedElement, expectedValue);
                    return false;
                }
            } else if (matchedElement instanceof String) {
                if (!((String) matchedElement).matches(expectedValue)) {
                    logNoMatch(modifiedJsonPathExpression, matchedElement, expectedValue);
                    return false;
                }
            } else if (matchedElement instanceof Boolean) {
                if (!matchedElement.equals(Boolean.valueOf(expectedValue))) {
                    logNoMatch(modifiedJsonPathExpression, matchedElement, expectedValue);
                    return false;
                }
            } else if (matchedElement instanceof Integer) {
                if (!matchedElement.equals(Integer.valueOf(expectedValue))) {
                    logNoMatch(modifiedJsonPathExpression, matchedElement, expectedValue);
                    return false;
                }
            } else {
                throw new IllegalStateException("JsonPath expression " + jsonPathExpression + " returns a type " + matchedElement.getClass().getCanonicalName() + " not yet handled. Please extend!");
            }
        }
        // All expressions in parameterExpressionMap have been found
        return true;
    }

    private static void logNoMatch(String modifiedJsonPathExpression, Object matchedElement, String expectedValue) {
        LOGGER.info("No match >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        LOGGER.info("No match {} expected:<{}> but was:<{}>",
                modifiedJsonPathExpression, expectedValue, matchedElement);
    }

    private static String makeJsonIfPlainString(String json) {
        json = json.trim();
        // by making an array with one element we can match it with $[0]
        if (!json.startsWith("{") && !json.startsWith("[")) {
            json = "[\"" + json + "\"]";
        }
        return json;
    }

    private static void checkMoreThanOneElementAndThrow(String json, String jsonPathExpression, List<Object> nonNullJsonArray) {
        if (nonNullJsonArray.size() > 1) {
            throw new IllegalArgumentException("JsonPath expression '" + jsonPathExpression +
                    "' is not expected to match more than one element in " + json +
                    "\nbut matches: " + nonNullJsonArray + "." +
                    "\nConsider using an absolute path to the element starting with a $ such as $[0].locationNumber. See JsonPath documentation."
            );
        }
    }

    private static List<Object> getAsNonNullList(JSONArray jsonArray) {
        List<Object> nonNullJsonArray = new ArrayList<>();
        for (Object o : jsonArray) {
            if (o != null) {
                nonNullJsonArray.add(o);
            }
        }
        return nonNullJsonArray;
    }

    private static String ensureJsonPathExpressionIfNotAbsolutePath(String jsonPathExpression) {
        if (jsonPathExpression.equals(VALUE_KEYWORD)) {
            return JSON_PATH_FIRST_ELEMENT_OF_ROOT_ARRAY;
        }
        return (!jsonPathExpression.startsWith(JSON_PATH_ROOT_ELEMENT)) ? JSON_PATH_FOR_ANY_CHILD_FROM_ROOT + jsonPathExpression : jsonPathExpression;
    }
}