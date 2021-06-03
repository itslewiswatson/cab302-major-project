package common.domain;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class FullAssetTest extends EntityTestHelper {
    private final FullAsset fullAsset;

    public FullAssetTest() {
        this.fullAsset = new FullAsset("FA_ID", "FA_NAME", LocalDate.EPOCH, 999);
    }

    @Test
    public void testEntity() {
        super.testEntity(fullAsset);
    }

    @Test
    public void testSerializable() {
        super.testSerializable(fullAsset);
    }

    @Test
    public void testGetAssetId() {
        Assert.assertEquals("FA_ID", fullAsset.getAssetId());
    }

    @Test
    public void testGetAssetName() {
        Assert.assertEquals("FA_NAME", fullAsset.getAssetName());
    }

    @Test
    public void testGetDateAdded() {
        Assert.assertEquals(LocalDate.EPOCH, fullAsset.getDateAdded());
    }

    @Test
    public void testGetAmount() {
        Assert.assertEquals(999, fullAsset.getAmount());
    }
}
