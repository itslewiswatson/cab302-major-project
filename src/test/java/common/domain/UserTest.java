import common.domain.User;
import org.junit.Assert;
import org.junit.Test;

public class UserTest {
    @Test
    public void testUserId() {
        User user = new User("ID123", "", "", false);
        Assert.assertEquals("ID123", user.getUserId());
    }
}
