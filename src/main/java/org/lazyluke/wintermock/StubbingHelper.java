package org.lazyluke.wintermock;

import java.io.IOException;
import org.lazyluke.wintermock.jackson.SimpleObjectMapperFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

public class StubbingHelper {

    public static final String WIREMOCK_URL = "http://localhost:8080/";

    public static String getAsJsonString(Object object) {
        try {
            String s = SimpleObjectMapperFactory.getInstance().writeValueAsString(object);
            return s;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO naming consistency convertJsonStringToObjectOfClass
    public static <T> T convertJsonStringToObjectOfClass(String reply, Class<T> valueType) {
        try {
            return SimpleObjectMapperFactory.getInstance().readValue(reply, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO naming consistency convertJsonStringToObjectOfClass
    public static <T> T fromJSON(final TypeReference<T> type, final String json) {
        T data;

        try {
            data = SimpleObjectMapperFactory.getInstance().readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}