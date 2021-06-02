package server;

import common.dataTypes.TradeType;
import common.domain.Trade;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class Reconciler extends TimerTask {

    /**
     * The database controller.
     */
    private final DBStatements dbStatements;

    public Reconciler(DBStatements dbStatements) {
        this.dbStatements = dbStatements;
    }

    @Override
    public void run() {
        ArrayList<Trade> activeTrades = dbStatements.getActiveTrades();

        ArrayList<Trade> buyTrades = activeTrades.stream().filter(trade -> trade.getType() == TradeType.BUY).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Trade> sellTrades = activeTrades.stream().filter(trade -> trade.getType() == TradeType.SELL).collect(Collectors.toCollection(ArrayList::new));
    }
}
