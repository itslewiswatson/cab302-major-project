package common.dto;

import org.junit.Assert;
import org.junit.Test;

public class GetUnitTradesDTOTest {
    @Test
    public void testGetUnit() {
        GetUnitTradesDTO dto = new GetUnitTradesDTO("UNIT");
        Assert.assertEquals("UNIT", dto.getUnitId());
    }
}
