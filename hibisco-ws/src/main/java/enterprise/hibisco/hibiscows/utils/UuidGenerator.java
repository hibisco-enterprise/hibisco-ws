package enterprise.hibisco.hibiscows.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidGenerator {

    private static UUID id;

    public static Short wrapUuid() {
        id = UUID.randomUUID();
        short returnedId;
        return returnedId = ByteBuffer.wrap(id.toString().getBytes()).getShort();
    }
}
