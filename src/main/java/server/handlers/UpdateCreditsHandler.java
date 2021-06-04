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
            Unit unit = resolveUnit(unitId);
            unit.setCredits(dto.getNewCredits());
            return (dbStatements.updateUnitCredits(unit)) ? unit : null;
        } catch (NullResultException e) {
            return null;
        }
    }
}
