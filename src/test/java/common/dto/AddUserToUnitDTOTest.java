package common.dto;

import org.junit.Assert;
import org.junit.Test;

public class AddUserToUnitDTOTest {
    @Test
    public void testGetUser() {
        AddUserToUnitDTO dto = new AddUserToUnitDTO("USER ID", "");
        Assert.assertEquals("USER ID", dto.getUserId());
    }

    @Test
    public void testGetUnit() {
        AddUserToUnitDTO dto = new AddUserToUnitDTO("", "UNIT ID");
        Assert.assertEquals("UNIT ID", dto.getUnitId());
    }
}
