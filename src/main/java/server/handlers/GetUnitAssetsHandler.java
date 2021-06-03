package server.handlers;

import common.domain.UnitAsset;
import common.dto.GetUnitAssetsDTO;
import server.db.DBStatements;

import java.util.ArrayList;

public class GetUnitAssetsHandler extends Handler<ArrayList<UnitAsset>, GetUnitAssetsDTO> {
    public GetUnitAssetsHandler(DBStatements dbStatements) {
        super(dbStatements);
    }

    @Override
    public ArrayList<UnitAsset> handle(GetUnitAssetsDTO dto) {
        return dbStatements.findUnitAssetsByUnit(dto.getUnitId());
    }
}
