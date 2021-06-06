package server.handlers;

import common.domain.UnitAsset;
import common.dto.RemoveUnitAssetDTO;
import common.exceptions.NullResultException;
import server.db.DBStrategy;

import java.sql.SQLException;

public class RemoveUnitAssetHandler extends Handler<Boolean, RemoveUnitAssetDTO> {
    public RemoveUnitAssetHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto Information from client request
     * @return Boolean indicating success
     */
    @Override
    public Boolean handle(RemoveUnitAssetDTO dto) {
        String unitId = dto.getUnitId();
        String assetId = dto.getAssetId();

        try {
            UnitAsset unitAsset = resolveUnitAsset(unitId, assetId);
            dbStatements.removeUnitAsset(unitAsset);
        } catch (NullResultException | SQLException e) {
            return false;
        }

        return true;
    }
}
