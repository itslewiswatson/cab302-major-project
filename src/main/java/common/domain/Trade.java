package common.domain;

import common.dataTypes.TradeType;
import org.jetbrains.annotations.Nullable;

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
    private final Unit unit;

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
    private @Nullable LocalDate dateFilled;

    /**
     * Creates a Trade object when a trade offer is retrieved from the database.
     *
     * @param tradeId        The trade offer's identifier.
     * @param unit           The organisational unit that listed the trade offer.
     * @param asset          The asset being listed in the trade offer.
     * @param user           The user who listed the trade offer.
     * @param dateListed     The date the trade offer was listed.
     * @param type           The trade offer's type.
     * @param quantity       The quantity of the asset being listed in the trade offer.
     * @param price          The trade offer's price.
     * @param quantityFilled The quantity current filled (default: 0)
     * @param dateFilled     The date which the entire order was filled.
     */
    public Trade(String tradeId, Unit unit, Asset asset, User user, LocalDate dateListed, TradeType type, int quantity, int price, int quantityFilled, @Nullable LocalDate dateFilled) {
        this.tradeId = tradeId;
        this.unit = unit;
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
    public Unit getUnit() {
        return unit;
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

    /**
     * Sets the date when the instance was filled
     *
     * @param dateFilled The date the trade was filled
     */
    public void setDateFilled(@Nullable LocalDate dateFilled) {
        this.dateFilled = dateFilled;
    }

    /**
     * Get the trade's date fulfilled
     *
     * @return Date if filled, null otherwise
     */
    public @Nullable LocalDate getDateFilled() {
        return dateFilled;
    }

    /**
     * Set the quantity of the trade that has been filled
     *
     * @param quantityFilled Integer representing how many assets have been filled
     */
    public void setQuantityFilled(int quantityFilled) {
        this.quantityFilled = quantityFilled;
    }

    /**
     * Adds to the quantity filled balance
     *
     * @param quantity Integer representing how many assets to add to the filled quantity
     */
    public void addQuantityFilled(int quantity) {
        quantityFilled += quantity;
    }
}