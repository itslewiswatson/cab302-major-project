package server.handlers;

import common.domain.Trade;
import common.dto.GetTradesDTO;
import server.db.DBStrategy;

import java.util.ArrayList;

/**
 * This class represents the get trades logic
 */
public class GetTradesHandler extends Handler<ArrayList<Trade>, GetTradesDTO> {
    public GetTradesHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto Information from client request
     * @return List of active trades or null
     */
    @Override
    public ArrayList<Trade> handle(GetTradesDTO dto) {
        ArrayList<Trade> trades = dbStatements.getActiveTrades();

        // Return null if no trades are returned
        return (trades.size() != 0) ? trades : null;
    }
}
