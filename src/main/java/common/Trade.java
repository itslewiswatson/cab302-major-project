package common;

import java.time.LocalDate;

/**
 * This class implements a trade offer.
 */
public class Trade implements java.io.Serializable {

    /* Fields */

    /**
     * Class version number.
     */
    private static final long serialVersionUID = 0;

    /**
     * An indication that the trade is to be removed from the database.
     */
    private boolean remove;

    /**
     * A trade's total price.
     */
    private int price;

    /**
     * The number of assets offered in the trade.
     */
    private int quantity;

    /**
     * The asset offered in the trade.
     */
    private String asset;

    /**
     * A trade's list date.
     */
    private LocalDate date;

    /**
     * The type of trade (BUY or SELL).
     */
    private String type;

    /**
     * The unit offering the trade.
     */
    private String unit;


    /* Constructors */

    /**
     * Client-side constructor to create a trade object when a new trade is listed.
     *
     * @param price A trade's total price.
     * @param quantity The number of assets offered in the trade.
     * @param asset The asset offered in the trade.
     * @param type The type of trade (BUY or SELL).
     * @param unit The unit offering the trade.
     */
    public Trade(int price, int quantity, String asset, String type, String unit) {
        this.remove = false;
        this.price = price;
        this.quantity = quantity;
        this.asset = asset;
        date = LocalDate.now();
        this.type = type;
        this.unit = unit;
    }

    /**
     * Server-side constructor to create a trade object when a trade is retrieved from the database.
     *
     * @param price A trade's total price.
     * @param quantity The number of assets offered in the trade.
     * @param asset The asset offered in the trade.
     * @param date A trade's list date.
     * @param type The type of trade (BUY or SELL).
     * @param unit The unit offering the trade.
     */
    public Trade(int price, int quantity, String asset, LocalDate date, String type, String unit) {
        this.remove = false;
        this.price = price;
        this.quantity = quantity;
        this.asset = asset;
        this.date = date;
        this.type = type;
        this.unit = unit;
    }


    /* Methods */

    /**
     * Set trade's remove field.
     *
     * @param remove An indication that the trade is to be removed.
     */
    public void setRemove(boolean remove) {
        this.remove = remove;
    }
}
