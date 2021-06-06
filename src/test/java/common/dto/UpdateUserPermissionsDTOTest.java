package common.dto;

import org.junit.Assert;
import org.junit.Test;

public class UpdateUserPermissionsDTOTest {
    @Test
    public void testGetUser() {
        UpdateUserPermissionsDTO dto = new UpdateUserPermissionsDTO("USER", false);
        Assert.assertEquals("USER", dto.getUserId());
    }

    @Test
    public void testIsAdminFalse() {
        UpdateUserPermissionsDTO dto = new UpdateUserPermissionsDTO("USER", false);
        Assert.assertFalse(dto.isAdmin());
    }

    @Test
    public void testIsAdminTrue() {
        UpdateUserPermissionsDTO dto = new UpdateUserPermissionsDTO("USER", true);
        Assert.assertTrue(dto.isAdmin());
    }
}
