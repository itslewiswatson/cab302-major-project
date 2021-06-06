package common.dto;

import org.junit.Assert;
import org.junit.Test;

public class AddAssetDTOTest {
    @Test
    public void testGetAssetId() {
        AddAssetDTO dto = new AddAssetDTO("NAME");
        Assert.assertEquals("NAME", dto.getAssetName());
    }
}
