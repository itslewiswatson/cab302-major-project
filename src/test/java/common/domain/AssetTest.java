package common.domain;

import org.junit.Assert;
import org.junit.Test;

public class AssetTest extends EntityTestHelper {
    private final Asset asset;

    public AssetTest() {
        this.asset = new Asset("ID4848", "poTATOES");
    }

    @Test
    public void testEntity() {
        super.testEntity(asset);
    }

    @Test
    public void testSerializable() {
        super.testSerializable(asset);
    }

    @Test
    public void testGetAssetId() {
        Assert.assertEquals("ID4848", asset.getAssetId());
    }

    @Test
    public void testGetAssetName() {
        Assert.assertEquals("poTATOES", asset.getAssetName());
    }
}
