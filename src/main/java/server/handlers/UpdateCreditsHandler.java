package server.handlers;

import common.domain.Unit;
import common.dto.UpdateCreditsDTO;
import server.db.DBStatements;

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

        return (dbStatements.updateUnitCredits(unit)) ?  unit : null;
    }

    private Unit resolveUnit(String unitId) {
        return dbStatements.findUnitById(unitId);
    }
}
