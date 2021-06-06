package common.dto;

import org.junit.Assert;
import org.junit.Test;

public class GetUnitUsersDTOTest {
    @Test
    public void testGetUnit() {
        GetUnitUsersDTO dto = new GetUnitUsersDTO("UNIT");
        Assert.assertEquals("UNIT", dto.getUnitId());
    }
}
