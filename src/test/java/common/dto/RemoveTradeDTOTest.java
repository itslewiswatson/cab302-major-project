package common.dto;

import org.junit.Assert;
import org.junit.Test;

public class RemoveTradeDTOTest {
    @Test
    public void testTrade() {
        RemoveTradeDTO dto = new RemoveTradeDTO("TRADE");
        Assert.assertEquals("TRADE", dto.getTradeId());
    }
}
