package common.dto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NewUserDTOTest {
    private NewUserDTO dto;

    @Before
    public void setUp() throws Exception {
        dto = new NewUserDTO("USER", "PASS", true);
    }

    @Test
    public void testGetUser() {
        Assert.assertEquals("USER", dto.getUsername());
    }

    @Test
    public void testGetPassword() {
        Assert.assertEquals("PASS", dto.getPassword());
    }

    @Test
    public void testIsAdmin() {
        Assert.assertTrue(dto.isAdmin());
    }
}
