package enterprise.hibisco.hibiscows.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidGenerator {

    private static UUID id;
    private static Long returnedId;

    private static void genId() {
        id = UUID.randomUUID();
    }

    public static Long wrapUuid() {
        genId();
        return returnedId = ByteBuffer.wrap(id.toString().getBytes()).getLong();
    }
}
