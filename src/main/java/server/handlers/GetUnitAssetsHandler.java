package server.handlers;

import common.domain.Unit;
import common.domain.UnitAsset;
import common.dto.GetUnitAssetsDTO;
import common.exceptions.NullResultException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import server.db.DBStrategy;

import java.util.ArrayList;

public class GetUnitAssetsHandler extends Handler<ArrayList<UnitAsset>, GetUnitAssetsDTO> {
    public GetUnitAssetsHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    @Override
    public ArrayList<UnitAsset> handle(GetUnitAssetsDTO dto) {
        try {
            Unit unit = resolveUnit(dto.getUnitId());
            return dbStatements.findUnitAssetsByUnit(unit.getUnitId());
        } catch (NullResultException e) {
            return null;
        }
    }

    private Unit resolveUnit(String unitId) throws NullResultException {
        Unit unit = dbStatements.findUnitById(unitId);
        if (unit == null) {
            throw new NullResultException();
        }
        return unit;
    }
}
