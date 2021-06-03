package common.services;

import org.junit.Assert;
import org.junit.Test;

public class UuidGeneratorTest {
    @Test
    public void testGenerateUuid() {
        Assert.assertNotNull(UuidGenerator.generateUuid());
    }

    @Test
    public void testUniqueUuid() {
        Assert.assertNotSame(UuidGenerator.generateUuid(), UuidGenerator.generateUuid());
    }

    @Test
    public void testUuidLength() {
        Assert.assertEquals(36, UuidGenerator.generateUuid().length());
    }
}
