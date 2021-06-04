package server.handlers;

import common.domain.Unit;
import common.dto.UpdateCreditsDTO;
import common.exceptions.NullResultException;
import server.db.DBStrategy;

public class UpdateCreditsHandler extends Handler<Unit, UpdateCreditsDTO> {
    public UpdateCreditsHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    @Override
    public Unit handle(UpdateCreditsDTO dto) {
        try {
            String unitId = dto.getUnitId();
            int newCredits = dto.getNewCredits();

            Unit unit = resolveUnit(unitId);
            unit.setCredits(newCredits);
            return (dbStatements.updateUnitCredits(unit)) ? unit : null;
        } catch (NullResultException e) {
            return null;
        }
    }
}
