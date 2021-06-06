package server;

import common.dataTypes.TradeType;
import common.domain.Asset;
import common.domain.Trade;
import common.domain.Unit;
import common.domain.UnitAsset;
import server.db.DBStatements;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.stream.Collectors;

class Reconciler extends TimerTask {

    /**
     * The database controller.
     */
    private final DBStatements dbStatements;

    private ArrayList<Trade> buyTrades;

    private ArrayList<Trade> sellTrades;

    public Reconciler(DBStatements dbStatements) {
        this.dbStatements = dbStatements;
    }

    @Override
    public void run() {
        getBuySellTrades();

        for (Trade buyTrade: new ArrayList<>(buyTrades)) {
            Trade sellTrade = findSuitableTrade(buyTrade);

            if (sellTrade != null)
            {
                reconcileTrades(buyTrade, sellTrade);
                getBuySellTrades();
            }
        }
    }

    private void getBuySellTrades() {
        ArrayList<Trade> activeTrades = dbStatements.getActiveTrades();

        buyTrades = activeTrades.stream()
                .filter(trade -> trade.getType() == TradeType.BUY)
                .collect(Collectors.toCollection(ArrayList::new));

        sellTrades = activeTrades.stream()
                .filter(trade -> trade.getType() == TradeType.SELL)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private Trade findSuitableTrade(Trade buyTrade) {
         return sellTrades.stream()
                 .filter(sellTrade -> sellTrade.getAsset().getAssetId().equals(buyTrade.getAsset().getAssetId())
                        && !sellTrade.getUnit().getUnitId().equals(buyTrade.getUnit().getUnitId())
                        && sellTrade.getPrice() <= buyTrade.getPrice())
                .findFirst()
                .orElse(null);
    }

    private void reconcileTrades(Trade buyTrade, Trade sellTrade) {
        Asset assetExchanged = buyTrade.getAsset();
        int buyQuantity = buyTrade.getQuantity() - buyTrade.getQuantityFilled();
        int sellQuantity = sellTrade.getQuantity() - sellTrade.getQuantityFilled();
        int quantityExchanged = buyQuantity;
        Unit buyingUnit = buyTrade.getUnit();
        Unit sellingUnit = sellTrade.getUnit();
        UnitAsset buyingUnitAsset = dbStatements.findUnitAsset(buyingUnit.getUnitId(), assetExchanged.getAssetId());

        if (buyQuantity == sellQuantity) {
            buyTrade.setQuantityFilled(quantityExchanged);
            buyTrade.setDateFilled(LocalDate.now());

            sellTrade.setQuantityFilled(quantityExchanged);
            sellTrade.setDateFilled(LocalDate.now());
        }
        else if (buyQuantity > sellQuantity) {
            quantityExchanged = sellQuantity;

            buyTrade.addQuantityFilled(quantityExchanged);

            sellTrade.setQuantityFilled(quantityExchanged);
            sellTrade.setDateFilled(LocalDate.now());
        }
        else {
            buyTrade.setQuantityFilled(quantityExchanged);
            buyTrade.setDateFilled(LocalDate.now());

            sellTrade.addQuantityFilled(quantityExchanged);
        }

        if (buyingUnitAsset != null) {
            buyingUnitAsset.addQuantity(quantityExchanged);
            dbStatements.updateUnitAsset(buyingUnitAsset);
        }
        else {
            dbStatements.addUnitAsset(new UnitAsset(buyingUnit.getUnitId(), assetExchanged, quantityExchanged));
        }

        sellingUnit.addCredits(quantityExchanged * sellTrade.getPrice());

        dbStatements.updateUnitCredits(sellingUnit);
        dbStatements.updateTrade(buyTrade);
        dbStatements.updateTrade(sellTrade);
    }
}
