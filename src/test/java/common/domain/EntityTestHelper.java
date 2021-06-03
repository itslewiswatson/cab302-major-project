package common.domain;

import org.junit.Assert;

import java.io.Serializable;

public class EntityTestHelper {
    public void testEntity(Object entity) {
        Assert.assertTrue(entity instanceof Entity);
    }

    public void testSerializable(Object entity) {
        Assert.assertTrue(entity instanceof Serializable);
    }
}
