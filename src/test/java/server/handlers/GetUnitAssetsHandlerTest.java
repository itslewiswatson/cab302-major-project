package server.handlers;

import common.domain.UnitAsset;
import common.dto.GetUnitAssetsDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.MockDBStatements;

import java.util.ArrayList;

public class GetUnitAssetsHandlerTest {
    private GetUnitAssetsHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new GetUnitAssetsHandler(new MockDBStatements());
    }

    @Test
    public void testHandle() {
        ArrayList<UnitAsset> unitAssets = handler.handle(new GetUnitAssetsDTO("ID11"));
        Assert.assertNotNull(unitAssets);

        for (UnitAsset unitAsset : unitAssets) {
            Assert.assertEquals("ID11", unitAsset.getUnitId());
        }
    }

    @Test
    public void testNullUnit() {
        ArrayList<UnitAsset> unitAssets = handler.handle(new GetUnitAssetsDTO("INVALID"));
        Assert.assertNull(unitAssets);
    }
}
