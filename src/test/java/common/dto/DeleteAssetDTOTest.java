package common.dto;

import org.junit.Assert;
import org.junit.Test;

public class DeleteAssetDTOTest {
    @Test
    public void testGetAsset() {
        DeleteAssetDTO dto = new DeleteAssetDTO("ASSET");
        Assert.assertEquals("ASSET", dto.getAssetId());
    }
}
