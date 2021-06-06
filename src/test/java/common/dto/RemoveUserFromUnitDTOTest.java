package common.dto;

import org.junit.Assert;
import org.junit.Test;

public class RemoveUserFromUnitDTOTest {
    @Test
    public void testGetUser() {
        RemoveUserFromUnitDTO dto = new RemoveUserFromUnitDTO("USER", "");
        Assert.assertEquals("USER", dto.getUserId());
    }

    @Test
    public void testGetUnit() {
        RemoveUserFromUnitDTO dto = new RemoveUserFromUnitDTO("", "UNIT");
        Assert.assertEquals("UNIT", dto.getUnitId());
    }
}
