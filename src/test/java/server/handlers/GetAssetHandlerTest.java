package server.handlers;

import common.domain.FullAsset;
import common.dto.GetAssetsDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.DBStrategy;
import server.db.MockDBStatements;

import java.util.ArrayList;

public class GetAssetHandlerTest {
    private GetAssetsHandler handler;
    private DBStrategy dbStrategy;

    @Before
    public void setUp() throws Exception {
        dbStrategy = new MockDBStatements();
        handler = new GetAssetsHandler(dbStrategy);
    }

    @Test
    public void testHandle() {
        ArrayList<FullAsset> assets = handler.handle(new GetAssetsDTO());
        Assert.assertNotNull(assets);
        Assert.assertEquals(dbStrategy.findAssets(), assets);
    }
}
