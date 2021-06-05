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

    @Override
    public Unit handle(NewUnitDTO dto) {
        try {
            @Nullable Unit existingUnit = dbStatements.findUnitByName(dto.getUnitName());
            if (existingUnit != null) {
                return null;
            }

            Unit unit = new Unit(
                    UuidGenerator.generateUuid(),
                    dto.getUnitName(),
                    0
            );

            dbStatements.addUnit(unit);
            return unit;
        } catch (SQLException e) {
            return null;
        }
    }
}
