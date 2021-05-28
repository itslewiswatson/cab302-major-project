package server.handlers;

import common.domain.Asset;
import common.domain.FullAsset;
import common.dto.AddAssetDTO;
import common.services.UuidGenerator;
import server.DBStatements;

import java.util.ArrayList;

public class AddAssetHandler extends Handler<ArrayList<FullAsset>, AddAssetDTO> {
    public AddAssetHandler(DBStatements dbStatements) {
        super(dbStatements);
    }

    @Override
    public ArrayList<FullAsset> handle(AddAssetDTO dto) {
        Asset newAsset = new Asset(UuidGenerator.generateUuid(), dto.getAssetName());
        dbStatements.addAsset(newAsset);
        return dbStatements.findAssets();
    }
}
