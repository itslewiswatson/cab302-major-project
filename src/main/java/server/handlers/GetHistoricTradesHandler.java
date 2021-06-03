package server.handlers;

import common.domain.Trade;
import common.dto.GetHistoricTradesDTO;
import server.db.DBStrategy;

import java.util.ArrayList;

public class GetHistoricTradesHandler extends Handler<ArrayList<Trade>, GetHistoricTradesDTO> {
    public GetHistoricTradesHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    @Override
    public ArrayList<Trade> handle(GetHistoricTradesDTO dto) {
        ArrayList<Trade> trades = dbStatements.fetchHistoricTrades();

        String unitId = dto.getUnitId();
        if (unitId != null) {
            trades.removeIf(trade -> !trade.getUnitId().equals(dto.getUnitId()));
            return trades;
        }

        return trades;
    }
}
