package server.handlers;

import common.domain.FullAsset;
import common.dto.DeleteAssetDTO;
import common.exceptions.NullResultException;
import server.db.DBStrategy;

import java.sql.SQLException;

public class DeleteAssetHandler extends Handler<Boolean, DeleteAssetDTO> {
    public DeleteAssetHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto Information from client request
     * @return Boolean indicating success
     */
    @Override
    public Boolean handle(DeleteAssetDTO dto) {
        try {
            FullAsset asset = resolveAsset(dto.getAssetId());
            if (asset.getAmount() > 0) {
                return false;
            }

            dbStatements.removeAsset(asset);
        } catch (SQLException | NullResultException exception) {
            return false;
        }
        return true;
    }
}
