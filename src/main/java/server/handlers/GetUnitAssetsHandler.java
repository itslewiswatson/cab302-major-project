package server.handlers;

import common.domain.Unit;
import common.domain.UnitAsset;
import common.dto.GetUnitAssetsDTO;
import common.exceptions.NullResultException;
import server.db.DBStrategy;

import java.util.ArrayList;

public class GetUnitAssetsHandler extends Handler<ArrayList<UnitAsset>, GetUnitAssetsDTO> {
    public GetUnitAssetsHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto Information from client request
     * @return List of all units or null
     */
    @Override
    public ArrayList<UnitAsset> handle(GetUnitAssetsDTO dto) {
        try {
            Unit unit = resolveUnit(dto.getUnitId());
            return dbStatements.findUnitAssetsByUnit(unit.getUnitId());
        } catch (NullResultException e) {
            return null;
        }
    }
}
