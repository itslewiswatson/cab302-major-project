package server.handlers;

import common.domain.Asset;
import common.dto.DeleteAssetDTO;
import common.exceptions.NullResultException;
import org.jetbrains.annotations.Nullable;
import server.db.DBStrategy;

import java.sql.SQLException;

public class DeleteAssetHandler extends Handler<Boolean, DeleteAssetDTO> {
    public DeleteAssetHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    @Override
    public Boolean handle(DeleteAssetDTO dto) {
        try {
            Asset asset = resolveAsset(dto.getAssetId());
            dbStatements.removeAsset(asset);
            return true;
        } catch (SQLException | NullResultException exception) {
            return false;
        }
    }

    private Asset resolveAsset(String assetId) throws NullResultException {
        @Nullable Asset asset = dbStatements.findAssetById(assetId);
        if (asset == null) {
            throw new NullResultException();
        }
        return asset;
    }
}
