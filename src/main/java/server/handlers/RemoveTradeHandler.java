package server.handlers;

import common.dataTypes.TradeType;
import common.domain.Asset;
import common.domain.Trade;
import common.domain.Unit;
import common.domain.UnitAsset;
import common.dto.RemoveTradeDTO;
import common.exceptions.NullResultException;
import server.db.DBStrategy;

import java.sql.SQLException;

public class RemoveTradeHandler extends Handler<Boolean, RemoveTradeDTO> {
    public RemoveTradeHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto Information from client request
     * @return Boolean indicating success
     */
    @Override
    public Boolean handle(RemoveTradeDTO dto) {
        try {
            Trade trade = resolveTrade(dto.getTradeId());
            Unit unit = resolveUnit(trade.getUnit().getUnitId());
            Asset asset = resolveAsset(trade.getAsset().getAssetId());
            int remainingQuantity = trade.getQuantity() - trade.getQuantityFilled();

            dbStatements.removeTrade(trade);

            if (trade.getType() == TradeType.BUY) {
                unit.addCredits(remainingQuantity * trade.getPrice());
                dbStatements.updateUnitCredits(unit);
            } else {
                UnitAsset unitAsset = resolveUnitAsset(unit.getUnitId(), asset.getAssetId());
                if (unitAsset == null) {
                    return null;
                }

                unitAsset.addQuantity(remainingQuantity);
                dbStatements.updateUnitAsset(unitAsset);
            }
        } catch (NullResultException | SQLException e) {
            return false;
        }
        return true;
    }

    private Trade resolveTrade(String tradeId) throws NullResultException {
        Trade trade = dbStatements.findTradeById(tradeId);
        if (trade == null) {
            throw new NullResultException();
        }
        return trade;
    }
}
