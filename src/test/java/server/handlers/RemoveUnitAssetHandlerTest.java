package server.handlers;

import common.dto.RemoveUnitAssetDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.MockDBStatements;

public class RemoveUnitAssetHandlerTest {
    private RemoveUnitAssetHandler handler;

    @Before
    public void setUp() {
        handler = new RemoveUnitAssetHandler(new MockDBStatements());
    }

    @Test
    public void testHandle() {
        Boolean success = handler.handle(new RemoveUnitAssetDTO("ID11", "ID8"));
        Assert.assertTrue(success);
    }

    @Test
    public void testBadUnit() {
        Boolean success = handler.handle(new RemoveUnitAssetDTO("INVALID", "ID6"));
        Assert.assertFalse(success);
    }

    @Test
    public void testBadAsset() {
        Boolean success = handler.handle(new RemoveUnitAssetDTO("ID10", "INVALID"));
        Assert.assertFalse(success);
    }
}
