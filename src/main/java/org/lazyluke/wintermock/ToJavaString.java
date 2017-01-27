package org.lazyluke.wintermock;

import java.util.Iterator;
import java.util.List;
import wiremock.com.google.common.base.Function;
public final class ToJavaString {

    public static final String UUID_REGEX = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";

    public static String getJavaString(List<FunctionCall> calls, Function<String, String> stringConverterForExpectedParametersRegexAsJsonString, Function<String, String> stringConverterForReturnValueAsJsonString) {
        final StringBuilder sb = new StringBuilder("FunctionCalls.create(").append(System.lineSeparator());

        for (Iterator<FunctionCall> iterator = calls.iterator(); iterator.hasNext(); ) {
            FunctionCall call = iterator.next();

            sb.append(call.toJavaString(stringConverterForExpectedParametersRegexAsJsonString, stringConverterForReturnValueAsJsonString));

            if (!iterator.hasNext()) {
                if (sb.charAt(sb.length() - 1) == ',') {
                    // delete final comma if any
                    sb.deleteCharAt(sb.length() - 1);
                }
            }
            sb.append(System.lineSeparator());
        }

        sb.append(System.lineSeparator()).append(");");

        return sb.toString();
    }

    public static final Function<String, String> replaceSingleQuoteWithDouble = new Function<String, String>() {

        @Override
        public String apply(String input) {
            return (input == null) ? "" : input.replace('\'', '"');
        }
    };

    public static final Function<String, String> replaceDoubleQuoteWithSingle = new Function<String, String>() {

        @Override
        public String apply(String input) {
            return (input == null) ? "" : input.replace('"', '\'');
        }
    };

    public static final Function<String, String> replaceUUIDsWithAnyMatch = new Function<String, String>() {

        @Override
        public String apply(String input) {
            return (input == null) ? "" : input.replaceAll(UUID_REGEX, ".*?");
        }
    };

    public static final Function<String, String> replaceIDsWithAnyMatch = new Function<String, String>() {

        @Override
        public String apply(String input) {
            return (input == null) ? "" : input.replaceAll("ID:[0-9a-f]{48}", ".*?");
        }
    };

    public static final Function<String, String> replaceDateTimesWithAnyMatch = new Function<String, String>() {

        @Override
        public String apply(String input) {
            return (input == null) ? "" :
                    input
                            .replaceAll("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]T[012][0-9]:[0-9][0-9]:[0-9][0-9]\\.[0-9][0-9][0-9]\\+[0-9][0-9][0-9][0-9]", ".*?")
                            .replaceAll("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9] [012][0-9]\\.[0-9][0-9]\\.[0-9][0-9]", ".*?")
                    ;
        }
    };

    public static final Function<String, String> replaceQuotedAsteriskWithLiteralAsterisk = new Function<String, String>() {

        @Override
        public String apply(String input) {
            return (input == null) ? "" : input.replaceAll("'\\*'", "'\\\\\\\\*'");
        }
    };

    public static final Function<String, String> replaceDoubleQuotedXmlWithAnyMatch = new Function<String, String>() {

        @Override
        public String apply(String input) {
            return (input == null) ? "" : input.replaceAll("\"<.xml.*?[^\\\\]\"", "\".*?\"");
        }
    };
}
