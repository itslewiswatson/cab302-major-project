package server.handlers;

import common.domain.Asset;
import common.dto.DeleteAssetDTO;
import server.db.DBStrategy;

import java.sql.SQLException;

public class DeleteAssetHandler extends Handler<Boolean, DeleteAssetDTO> {
    public DeleteAssetHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    @Override
    public Boolean handle(DeleteAssetDTO dto) {
        Asset asset = resolveAsset(dto.getAssetId());
        if (asset == null) {
            return false;
        }

        try {
            dbStatements.removeAsset(asset);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    private Asset resolveAsset(String assetId) {
        return dbStatements.findAssetById(assetId);
    }
}
