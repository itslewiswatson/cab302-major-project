package server.handlers;

import common.domain.UnitAsset;
import common.dto.CreateOrUpdateUnitAssetDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.MockDBStatements;

import java.util.ArrayList;

public class CreateOrUpdateUnitAssetHandlerTest {
    private CreateOrUpdateUnitAssetHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new CreateOrUpdateUnitAssetHandler(new MockDBStatements());
    }

    @Test
    public void testHandle() {
        ArrayList<UnitAsset> unitAssets = handler.handle(new CreateOrUpdateUnitAssetDTO("ID10", "ID6", 10));
        Assert.assertNotNull(unitAssets);
    }

    @Test
    public void testBadUnit() {
        ArrayList<UnitAsset> unitAssets = handler.handle(new CreateOrUpdateUnitAssetDTO("INVALID", "ID6", 10));
        Assert.assertNull(unitAssets);
    }

    @Test
    public void testBadAsset() {
        ArrayList<UnitAsset> unitAssets = handler.handle(new CreateOrUpdateUnitAssetDTO("ID12", "INVALID", 10));
        Assert.assertNull(unitAssets);
    }
}
