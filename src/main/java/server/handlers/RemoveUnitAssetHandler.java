package server.handlers;

import common.domain.UnitAsset;
import common.dto.RemoveUnitAssetDTO;
import server.db.DBStatements;

public class RemoveUnitAssetHandler extends Handler<Boolean, RemoveUnitAssetDTO> {
    public RemoveUnitAssetHandler(DBStatements dbStatements) {
        super(dbStatements);
    }

    @Override
    public Boolean handle(RemoveUnitAssetDTO dto) {
        String unitId = dto.getUnitId();
        String assetId = dto.getAssetId();
        UnitAsset unitAsset = resolveUnitAsset(unitId, assetId);
        if (unitAsset == null) {
            return false;
        }

        try {
            dbStatements.removeUnitAsset(unitAsset);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private UnitAsset resolveUnitAsset(String unitId, String assetId) {
        return dbStatements.findUnitAsset(unitId, assetId);
    }
}
