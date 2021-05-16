package common.services;

import java.util.UUID;

/**
 * Generate a UUID from anywhere
 */
public class UuidGenerator {

    /**
     * Generate a UUID for domain objects
     *
     * @return A unique UUID
     */
    public static String generateUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
