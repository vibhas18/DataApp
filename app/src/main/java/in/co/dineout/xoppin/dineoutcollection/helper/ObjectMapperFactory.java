package in.co.dineout.xoppin.dineoutcollection.helper;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by suraj on 03/02/16.
 */
public class ObjectMapperFactory {
    private static ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {
        if (null == objectMapper) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

}
