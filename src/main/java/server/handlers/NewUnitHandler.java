package server.handlers;

import common.domain.Unit;
import common.dto.NewUnitDTO;
import common.services.UuidGenerator;
import org.jetbrains.annotations.Nullable;
import server.db.DBStrategy;

import java.sql.SQLException;

public class NewUnitHandler extends Handler<Unit, NewUnitDTO> {
    public NewUnitHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto Information from client request
     * @return Newly created unit or null
     */
    @Override
    public Unit handle(NewUnitDTO dto) {
        try {
            @Nullable Unit existingUnit = dbStatements.findUnitByName(dto.getUnitName());
            if (existingUnit != null || dto.getCredits() == null || dto.getCredits() < 0) {
                return null;
            }

            Unit unit = new Unit(
                    UuidGenerator.generateUuid(),
                    dto.getUnitName(),
                    dto.getCredits()
            );

            dbStatements.addUnit(unit);
            return unit;
        } catch (SQLException e) {
            return null;
        }
    }
}
