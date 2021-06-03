package common.services;

import org.junit.Assert;
import org.junit.Test;

public class PasswordHasherTest {
    @Test
    public void testHashResultNotNull() {
        Assert.assertNotNull(PasswordHasher.hashPassword("randomstring"));
    }

    @Test
    public void testHashLength() {
        Assert.assertEquals(60, PasswordHasher.hashPassword("wnkdjhrfhubhehfebhrbhjfebhfebhjfbhje").length());
    }

    @Test
    public void testCheckPassword() {
        Assert.assertTrue(PasswordHasher.checkPassword("plain", PasswordHasher.hashPassword("plain")));
    }

    @Test
    public void testCheckPasswordValidation() {
        Assert.assertFalse(PasswordHasher.checkPassword("VALUE 1", PasswordHasher.hashPassword("VALUE 2")));
    }
}
