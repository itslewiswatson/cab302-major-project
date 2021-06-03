package server.handlers;

import common.domain.Asset;
import common.domain.Trade;
import common.domain.User;
import common.dto.NewTradeDTO;
import common.services.UuidGenerator;
import server.db.DBStrategy;

import java.time.LocalDate;

public class NewTradeHandler extends Handler<Trade, NewTradeDTO> {

    public NewTradeHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto New trade dto
     * @return Newly constructed trade offer.
     */
    @Override
    public Trade handle(NewTradeDTO dto) {
        // TODO check pricing, check user, check unit, etc
        String assetId = dto.getAssetId();
        Asset asset = new Asset(assetId, "");
        User user = new User(dto.getUserId(), "", "", false);

        Trade newTrade = new Trade(
                UuidGenerator.generateUuid(),
                dto.getUnitId(),
                asset,
                user,
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
