package server.handlers;

import common.dataTypes.TradeType;
import common.domain.*;
import common.dto.NewTradeDTO;
import common.exceptions.NullResultException;
import common.services.UuidGenerator;
import server.db.DBStrategy;

import java.time.LocalDate;

/**
 * This class represents the new trade logic
 */
public class NewTradeHandler extends Handler<Trade, NewTradeDTO> {

    public NewTradeHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto Information from client request
     * @return Newly constructed trade offer.
     */
    @Override
    public Trade handle(NewTradeDTO dto) {

        try {
            Unit unit = resolveUnit(dto.getUnitId());
            FullAsset asset = resolveAsset(dto.getAssetId());
            User user = resolveUser(dto.getUserId());

            if (dto.getPrice() < 0 || dto.getQuantity() < 0) {
                return null;
            }

            // Check conditions for each type of trade
            if (dto.getType() == TradeType.BUY) {
                int totalPrice = dto.getPrice() * dto.getQuantity();
                int unitCredits = unit.getCredits();

                if (totalPrice > unitCredits) {
                    return null;
                }
            } else {
                UnitAsset unitAsset = resolveUnitAsset(unit.getUnitId(), asset.getAssetId());
                if (unitAsset == null) {
                    return null;
                }

                if (dto.getQuantity() > unitAsset.getQuantity()) {
                    return null;
                }
            }

            Trade newTrade = new Trade(
                    UuidGenerator.generateUuid(),
                    unit,
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

            // Hold credits in use for the trade, and set quantities as appropriate
            if (newTrade.getType() == TradeType.BUY) {
                unit.subtractCredits(newTrade.getQuantity() * newTrade.getPrice());
                dbStatements.updateUnitCredits(unit);
            } else {
                UnitAsset unitAsset = resolveUnitAsset(unit.getUnitId(), asset.getAssetId());
                unitAsset.subtractQuantity(newTrade.getQuantity());
                dbStatements.updateUnitAsset(unitAsset);
            }

            return newTrade;
        } catch (NullResultException e) {
            return null;
        }
    }
}
