package server.handlers;

import common.domain.FullAsset;
import common.dto.AddAssetDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.DBStrategy;
import server.db.MockDBStatements;

import java.util.ArrayList;

public class AddAssetHandlerTest {
    private AddAssetHandler handler;
    private DBStrategy dbStrategy;
    private AddAssetDTO mockDTO;

    @Before
    public void setUp() throws Exception {
        dbStrategy = new MockDBStatements();
        mockDTO = new AddAssetDTO("ASSET_NAME");
        handler = new AddAssetHandler(dbStrategy);
    }

    @Test
    public void testHandle() {
        ArrayList<FullAsset> assets = dbStrategy.findAssets();
        ArrayList<FullAsset> results = handler.handle(mockDTO);

        Assert.assertNotNull(results);
        Assert.assertSame(assets, results);
    }
}
