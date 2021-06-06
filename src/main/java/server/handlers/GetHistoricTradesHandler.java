package server.handlers;

import common.domain.Trade;
import common.dto.GetHistoricTradesDTO;
import server.db.DBStrategy;

import java.util.ArrayList;

/**
 * This class represents the get historic trades logic
 */
public class GetHistoricTradesHandler extends Handler<ArrayList<Trade>, GetHistoricTradesDTO> {
    public GetHistoricTradesHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto Information from client request
     * @return List of historic trades or null
     */
    @Override
    public ArrayList<Trade> handle(GetHistoricTradesDTO dto) {
        ArrayList<Trade> trades = dbStatements.fetchHistoricTrades();

        // Return null if no trades are returned
        return (trades.size() != 0) ? trades : null;
    }
}
