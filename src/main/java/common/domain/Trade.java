package common.domain;

import common.dataTypes.TradeType;

import java.time.LocalDate;

/**
 * This class represents a trade offer.
 */
public class Trade extends Entity {

    /**
     * The trade offer's identifier.
     */
    private final String tradeId;

    /**
     * The organisational unit that listed the trade offer.
     */
    private final String unitId;

    /**
     * The asset being listed in the trade offer.
     */
    private final Asset asset;

    /**
     * The date the trade offer was listed.
     */
    private final LocalDate dateListed;

    /**
     * The user who listed the trade offer.
     */
    private final User user;

    /**
     * The trade offer's type.
     */
    private final TradeType type;

    /**
     * The quantity of the asset being listed in the trade offer.
     */
    private final int quantity;

    /**
     * The trade offer's price.
     */
    private final int price;

    /**
     * The quantity current filled.
     */
    private int quantityFilled;

    /**
     * The date when the entire trade has been fulfilled.
     */
    private LocalDate dateFilled;

    /**
     * Creates a Trade object when a trade offer is retrieved from the database.
     *
     * @param tradeId        The trade offer's identifier.
     * @param unitId         The organisational unit that listed the trade offer.
     * @param asset          The asset being listed in the trade offer.
     * @param user           The user who listed the trade offer.
     * @param dateListed     The date the trade offer was listed.
     * @param type           The trade offer's type.
     * @param quantity       The quantity of the asset being listed in the trade offer.
     * @param price          The trade offer's price.
     * @param quantityFilled The quantity current filled (default: 0)
     * @param dateFilled     The date which the entire order was filled.
     */
    public Trade(String tradeId, String unitId, Asset asset, User user, LocalDate dateListed, TradeType type, int quantity, int price, int quantityFilled, LocalDate dateFilled) {
        this.tradeId = tradeId;
        this.unitId = unitId;
        this.asset = asset;
        this.user = user;
        this.dateListed = dateListed;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.quantityFilled = quantityFilled;
        this.dateFilled = dateFilled;
    }

    /**
     * Gets the trade offer's id field.
     *
     * @return The trade offer's identifier.
     */
    public String getTradeId() {
        return tradeId;
    }

    /**
     * Gets the trade offer's unit field.
     *
     * @return The organisational unit that listed the trade offer.
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * Gets the trade offer's asset field.
     *
     * @return The asset being listed in the trade offer.
     */
    public Asset getAsset() {
        return asset;
    }

    /**
     * Gets the user who listed the trade.
     *
     * @return The user instance of whoever initiated the trade offer.
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets the trade offer's date field.
     *
     * @return The date the trade offer was listed.
     */
    public LocalDate getDateListed() {
        return dateListed;
    }

    /**
     * Gets the trade offer's type field.
     *
     * @return The trade offer's type.
     */
    public TradeType getType() {
        return type;
    }

    /**
     * Gets the trade offer's quantity field.
     *
     * @return The quantity of the asset being listed in the trade offer.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the trade offer's price field.
     *
     * @return The trade offer's price.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Get the current quantity that has been filled
     *
     * @return The current quantity filled
     */
    public int getQuantityFilled() {
        return quantityFilled;
    }

    public void setDateFilled(LocalDate dateFilled) {
        this.dateFilled = dateFilled;
    }

    /**
     * Get the trade's date fulfilled
     *
     * @return Date if filled, null otherwise
     */
    public LocalDate getDateFilled() {
        return dateFilled;
    }

    public void setQuantityFilled(int quantityFilled) {
        this.quantityFilled = quantityFilled;
    }

    public void addQuantityFilled(int quantity) {
        quantityFilled += quantity;
    }
}