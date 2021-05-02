package common;

import java.time.LocalDate;

/**
 * This class represents a trade offer.
 *
 * @author Mitchell Egan
 */
public class Trade implements java.io.Serializable {

    /**
     * The class' version number.
     */
    private static final long serialVersionUID = 0;

    /**
     * The trade offer's identifier.
     */
    private final int id;

    /**
     * The organisational unit that listed the trade offer.
     */
    private final String unit;

    /**
     * The asset being listed in the trade offer.
     */
    private final String asset;

    /**
     * The date the trade offer was listed.
     */
    private final LocalDate date;

    /**
     * The trade offer's type.
     */
    private final String type;

    /**
     * The quantity of the asset being listed in the trade offer.
     */
    private final int quantity;

    /**
     * The trade offer's price.
     */
    private final int price;

    /**
     * Creates a Trade object when a trade offer is to be removed.
     *
     * @param id The trade offer's identifier.
     */
    public Trade(int id) {
        this.id = id;
        unit = "";
        asset = "";
        date = null;
        this.type = "";
        this.quantity = 0;
        this.price = 0;
    }

    /**
     * Creates a Trade object when a new trade offer is listed.
     *
     * @param unit The organisational unit that listed the trade offer.
     * @param asset The asset being listed in the trade offer.
     * @param type The trade offer's type.
     * @param quantity The quantity of the asset being listed in the trade offer.
     * @param price The trade offer's price.
     */
    public Trade(String unit, String asset, String type, int quantity, int price) {
        id = 0;
        this.unit = unit;
        this.asset = asset;
        date = LocalDate.now();
        this.type = type;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Creates a Trade object when a trade offer is retrieved from the database.
     *
     * @param id The trade offer's identifier.
     * @param unit The organisational unit that listed the trade offer.
     * @param asset The asset being listed in the trade offer.
     * @param date The date the trade offer was listed.
     * @param type The trade offer's type.
     * @param quantity The quantity of the asset being listed in the trade offer.
     * @param price The trade offer's price.
     */
    public Trade(int id, String unit, String asset, LocalDate date, String type, int quantity, int price) {
        this.id = id;
        this.unit = unit;
        this.asset = asset;
        this.date = date;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Gets the trade offer's id field.
     *
     * @return The trade offer's identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the trade offer's unit field.
     *
     * @return The organisational unit that listed the trade offer.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Gets the trade offer's asset field.
     *
     * @return The asset being listed in the trade offer.
     */
    public String getAsset() {
        return asset;
    }

    /**
     * Gets the trade offer's date field.
     *
     * @return The date the trade offer was listed.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the trade offer's type field.
     *
     * @return The trade offer's type.
     */
    public String getType() {
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
}