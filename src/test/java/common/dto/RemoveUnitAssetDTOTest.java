package common.dto;

import org.junit.Assert;
import org.junit.Test;

public class RemoveUnitAssetDTOTest {
    @Test
    public void testGetUnit() {
        RemoveUnitAssetDTO dto = new RemoveUnitAssetDTO("UNIT", "");
        Assert.assertEquals("UNIT", dto.getUnitId());
    }

    @Test
    public void testGetAsset() {
        RemoveUnitAssetDTO dto = new RemoveUnitAssetDTO("", "ASSET");
        Assert.assertEquals("ASSET", dto.getAssetId());
    }
}
