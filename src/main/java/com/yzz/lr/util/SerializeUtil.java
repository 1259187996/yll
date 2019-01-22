package com.yzz.lr.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author zhizhuang.yang
 * @date 2017年9月13日
 * @version 1.0.0
 * @description 序列化工具类
 */
public class SerializeUtil implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(SerializeUtil.class);

	public static byte[] serialize(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("serialize error");
        } finally {
            close(os);
            close(bos);
        }
        return rv;
    }
 
    public static Object unserialize(byte[] in) {
        return unserialize(in, Object.class);
    }
 
    @SuppressWarnings("unchecked")
    public static <T> T unserialize(byte[] in, Class<T> requiredType) {
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("deserialize error");
        } finally {
            close(is);
            close(bis);
        }
        return (T) rv;
    }
 
    private static void close(Closeable closeable) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.info("close stream error");
            }
    }
	
}
