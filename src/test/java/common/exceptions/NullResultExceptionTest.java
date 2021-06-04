package common.exceptions;

import org.junit.Assert;
import org.junit.Test;

public class NullResultExceptionTest {
    @Test
    public void testException() {
        Assert.assertEquals(Exception.class, NullResultException.class.getSuperclass());
    }
}
