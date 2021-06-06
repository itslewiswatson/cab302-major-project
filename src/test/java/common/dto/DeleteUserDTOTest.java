package common.dto;

import org.junit.Assert;
import org.junit.Test;

public class DeleteUserDTOTest {
    @Test
    public void testGetUser() {
        DeleteUserDTO dto = new DeleteUserDTO("USER");
        Assert.assertEquals("USER", dto.getUserId());
    }
}
