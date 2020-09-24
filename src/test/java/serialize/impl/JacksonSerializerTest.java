package serialize.impl;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class JacksonSerializerTest {

    @Test
    public void should_serialize_object_and_deserialize_from_bytes() {
        JacksonSerializer jacksonSerializer = new JacksonSerializer();
        SerializeObject serializeObject = new SerializeObject("java", "Java");
        byte[] bytes = jacksonSerializer.serialize(serializeObject);
        assertNotEquals(bytes.length, 0);
        SerializeObject deserializeObject = jacksonSerializer.deserialize(bytes, SerializeObject.class);
        assertEquals(serializeObject, deserializeObject);

    }

}