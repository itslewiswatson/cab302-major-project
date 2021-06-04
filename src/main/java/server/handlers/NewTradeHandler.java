package server.handlers;

import common.dataTypes.TradeType;
import common.domain.*;
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

        Unit unit = resolveUnit(dto.getUnitId());
        if (unit == null) {
            return null;
        }

        Asset asset = resolveAsset(dto.getAssetId());
        if (asset == null) {
            return null;
        }

        User user = resolveUser(dto.getUserId());
        if (user == null) {
            return null;
        }

        if (dto.getType() == TradeType.BUY) {
            int totalPrice = dto.getPrice() * dto.getQuantity();
            int unitCredits = unit.getCredits();

            if (totalPrice > unitCredits)
            {
                return null;
            }
        }
        else {
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

        if (newTrade.getType() == TradeType.BUY)
        {
            unit.subtractCredits(newTrade.getQuantity() * newTrade.getPrice());
            dbStatements.updateUnitCredits(unit);
        }
        else {
            UnitAsset unitAsset = resolveUnitAsset(unit.getUnitId(), asset.getAssetId());
            unitAsset.subtractQuantity(newTrade.getQuantity());
            dbStatements.updateUnitAsset(unitAsset);
        }

        return newTrade;
    }

    private Unit resolveUnit(String unitId) {
        return dbStatements.findUnitById(unitId);
    }

    private Asset resolveAsset(String assetId) {
        return dbStatements.findAssetById(assetId);
    }

    private User resolveUser(String userId) {
        return dbStatements.findUserById(userId);
    }

    private UnitAsset resolveUnitAsset(String unitId, String assetId) {
        return dbStatements.findUnitAsset(unitId, assetId);
    }
}
