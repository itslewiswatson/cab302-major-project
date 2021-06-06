package common.dto;

import org.junit.Assert;
import org.junit.Test;

public class UpdatePasswordDTOTest {
    @Test
    public void testGetUser() {
        UpdatePasswordDTO dto = new UpdatePasswordDTO("USER", "PASS");
        Assert.assertEquals("USER", dto.getUserId());
    }

    @Test
    public void testGetPassword() {
        UpdatePasswordDTO dto = new UpdatePasswordDTO("USER", "PASS");
        Assert.assertEquals("PASS", dto.getNewPassword());
    }
}
