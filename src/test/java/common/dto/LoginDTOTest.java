package common.dto;

import org.junit.Assert;
import org.junit.Test;

public class LoginDTOTest {
    @Test
    public void testGetUsername() {
        LoginDTO dto = new LoginDTO("USERNAME");
        Assert.assertEquals("USERNAME", dto.getUsername());
    }
}
