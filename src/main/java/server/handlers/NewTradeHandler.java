package server.handlers;

import common.domain.Trade;
import common.dto.NewTradeDTO;
import common.services.UuidGenerator;
import server.DBStatements;

import java.time.LocalDate;

public class NewTradeHandler extends Handler<Trade, NewTradeDTO> {

    public NewTradeHandler(DBStatements dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto New trade dto
     * @return Newly constructed trade offer.
     */
    @Override
    public Trade handle(NewTradeDTO dto) {
        // TODO check pricing, check user, check unit, etc

        Trade newTrade = new Trade(
                UuidGenerator.generateUuid(),
                dto.getUnitId(),
                dto.getAssetId(),
                dto.getUserId(),
                LocalDate.now(),
                dto.getType(),
                dto.getQuantity(),
                dto.getPrice(),
                0,
                null
        );

        dbStatements.createTrade(newTrade);

        return newTrade;
    }
}
