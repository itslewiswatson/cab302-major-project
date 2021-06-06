package common.dto;

import org.junit.Assert;
import org.junit.Test;

public class GetUnitAssetsDTOTest {
    @Test
    public void testGetUnit() {
        GetUnitAssetsDTO dto = new GetUnitAssetsDTO("UNIT");
        Assert.assertEquals("UNIT", dto.getUnitId());
    }
}
