package server.handlers;

import common.domain.Unit;
import common.dto.UpdateCreditsDTO;
import server.DBStatements;

import java.sql.SQLException;

public class UpdateCreditsHandler extends Handler<Unit, UpdateCreditsDTO> {
    public UpdateCreditsHandler(DBStatements dbStatements) {
        super(dbStatements);
    }

    @Override
    public Unit handle(UpdateCreditsDTO dto) {
        String unitId = dto.getUnitId();
        int newCredits = dto.getNewCredits();

        Unit unit = resolveUnit(unitId);
        if (unit == null) {
            return null;
        }

        unit.setCredits(newCredits);
        try {
            dbStatements.updateUnitCredits(unit);
        } catch (SQLException exception) {
            return null;
        }

        return unit;
    }

    private Unit resolveUnit(String unitId) {
        return dbStatements.findUnitById(unitId);
    }
}