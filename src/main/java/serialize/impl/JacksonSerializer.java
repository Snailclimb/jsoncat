package serialize.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import serialize.Serializer;

import java.io.IOException;

/**
 * @author shuang.kou
 * @createTime 2020年09月22日 18:03:00
 **/
public class JacksonSerializer implements Serializer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object object) {
        byte[] bytes = new byte[0];
        try {
            // Java object to JSON string, default compact-print
            bytes = objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        T object = null;
        try {
            object = objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }
}
