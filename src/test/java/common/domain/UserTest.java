package common.domain;

import org.junit.Assert;
import org.junit.Test;

public class UserTest extends EntityTestHelper {
    private final User user;

    public UserTest() {
        this.user = new User("ID123", "linus_torvalds", "debian", false);
    }

    @Test
    public void testEntity() {
        super.testEntity(user);
    }

    @Test
    public void testSerializable() {
        super.testSerializable(user);
    }

    @Test
    public void testGetUserId() {
        Assert.assertEquals("ID123", user.getUserId());
    }

    @Test
    public void testGetUsername() {
        Assert.assertEquals("linus_torvalds", user.getUsername());
    }

    @Test
    public void testGetPassword() {
        Assert.assertEquals("debian", user.getPassword());
    }

    @Test
    public void testIsAdmin() {
        Assert.assertFalse(user.isAdmin());
    }
}
