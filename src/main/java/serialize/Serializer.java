package serialize;

/**
 * @author shuang.kou
 * @createTime 2020年09月22日 18:02:00
 **/
public interface Serializer {
    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
