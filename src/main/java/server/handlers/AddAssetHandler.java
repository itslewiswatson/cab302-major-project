package server.handlers;

import common.domain.Asset;
import common.domain.FullAsset;
import common.dto.AddAssetDTO;
import common.services.UuidGenerator;
import org.jetbrains.annotations.Nullable;
import server.db.DBStrategy;

import java.util.ArrayList;

public class AddAssetHandler extends Handler<ArrayList<FullAsset>, AddAssetDTO> {
    public AddAssetHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    @Override
    public ArrayList<FullAsset> handle(AddAssetDTO dto) {
        Asset newAsset = new Asset(UuidGenerator.generateUuid(), dto.getAssetName());

        if (assetExists(newAsset.getAssetName())) return null;

        dbStatements.addAsset(newAsset);
        return dbStatements.findAssets();
    }

    private boolean assetExists(String assetName) {
        @Nullable Asset asset = dbStatements.findAssetByName(assetName);
        return asset != null;
    }
}
