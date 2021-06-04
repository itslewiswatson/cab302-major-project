package server.handlers;

import common.dto.DeleteAssetDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.DBStrategy;
import server.db.MockDBStatements;

public class DeleteAssetHandlerTest {
    private DBStrategy dbStrategy;

    @Before
    public void setUp() throws Exception {
        dbStrategy = new MockDBStatements();
    }

    @Test
    public void testHandle() {
        DeleteAssetHandler handler = new DeleteAssetHandler(dbStrategy);
        DeleteAssetDTO dto = new DeleteAssetDTO("ID9");
        boolean success = handler.handle(dto);
        Assert.assertTrue(success);
    }

    @Test
    public void testHandleInvalidAsset() {
        DeleteAssetHandler handler = new DeleteAssetHandler(dbStrategy);

        DeleteAssetDTO badDTO = new DeleteAssetDTO("INVALID");
        Assert.assertFalse(handler.handle(badDTO));
    }
}
