package common.exceptions;

import org.junit.Assert;
import org.junit.Test;

public class RouteNotFoundExceptionTest {
    @Test
    public void testException() {
        Assert.assertEquals(Exception.class, RouteNotFoundException.class.getSuperclass());
    }
}
