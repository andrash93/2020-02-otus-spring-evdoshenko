package ru.mango.vpbx.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;


public class JsonRedisSerializer implements RedisSerializer<Object> {

    private final ObjectMapper mapper;
    private final JdkSerializationRedisSerializer serializer;

    public JsonRedisSerializer() {
        this.mapper = new ObjectMapper().enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        this.serializer = new JdkSerializationRedisSerializer();
    }

    @Override
    public byte[] serialize(Object t) throws SerializationException {
        try {
            return mapper.writeValueAsBytes(t);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }
        if (bytes.length > 2 && bytes[0] == (byte) 0xAC && bytes[1] == (byte) 0xED) {
            return this.serializer.deserialize(bytes);
        }
        try {
            return mapper.readValue(bytes, Object.class);
        } catch (JsonParseException e) {
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toHexString(Byte.toUnsignedInt(b))).append(" ");
            }
            return this.serializer.deserialize(bytes);
        } catch (Exception e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }

}
