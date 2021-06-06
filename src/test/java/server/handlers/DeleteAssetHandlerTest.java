package server.handlers;

import common.dto.DeleteAssetDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.MockDBStatements;

public class DeleteAssetHandlerTest {
    private DeleteAssetHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new DeleteAssetHandler(new MockDBStatements());
    }

    @Test
    public void testHandle() {
        DeleteAssetDTO dto = new DeleteAssetDTO("ID404");
        boolean success = handler.handle(dto);
        Assert.assertTrue(success);
    }

    @Test
    public void testHandleInvalidAsset() {
        DeleteAssetDTO badDTO = new DeleteAssetDTO("INVALID");
        boolean success = handler.handle(badDTO);
        Assert.assertFalse(success);
    }

    @Test
    public void testOnlyDeleteEmptyAssets() {
        DeleteAssetDTO badDTO = new DeleteAssetDTO("ID9");
        boolean success = handler.handle(badDTO);
        Assert.assertFalse(success);
    }
}
