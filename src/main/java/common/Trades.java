package common;

import java.time.LocalDate;
import java.util.List;

/**
 * This class represents a collection of trade offers.
 *
 * @author Mitchell Egan
 */
public class Trades implements java.io.Serializable {

    /**
     * The class' version number.
     */
    private static final long serialVersionUID = 0;

    /**
     * A collection of trade offers.
     */
    private final List<Trade> trades;

    /**
     * Creates a Trades object when a collection of trade offers is to be sent over the network.
     *
     * @param trades A collection of trade offers.
     */
    public Trades(List<Trade> trades) {
        this.trades = trades;
    }

    /**
     * Gets the trade offers' trades field.
     *
     * @return A collection of trade offers.
     */
    public List<Trade> getTrades() {
        return trades;
    }

    /**
     * Sort the trade offers by organisational unit.
     */
    public void sortByUnit() {
        // To implement
    }

    /**
     * Sort the trade offers by asset.
     */
    public void sortByAsset() {
        // To implement
    }

    /**
     * Sort the trade offers by date.
     */
    public void sortByDate() {
        // To implement
    }

    /**
     * Sort the trade offers by type.
     */
    public void sortByType() {
        // To implement
    }

    /**
     * Sort the trade offers by quantity.
     */
    public void sortByQuantity() {
        // To implement
    }

    /**
     * Sort the trade offers by price.
     */
    public void sortByPrice() {
        // To implement
    }

    /**
     * Filter the trade offers by organisational unit.
     *
     * @param unit The organisational unit to filter by.
     * @return A new Trades object containing the filtered trades.
     */
    public Trades filterByUnit(String unit)
    {
        //To implement

        return null;
    }

    /**
     * Filter the trade offers by asset.
     *
     * @param asset The asset to filter by.
     * @return A new Trades object containing the filtered trades.
     */
    public Trades filterByAsset(String asset)
    {
        //To implement

        return null;
    }

    /**
     * Filter the trade offers by date.
     *
     * @param date The date to filter by.
     * @return A new Trades object containing the filtered trades.
     */
    public Trades filterByDate(LocalDate date)
    {
        //To implement

        return null;
    }

    /**
     * Filter the trade offers by type.
     *
     * @param type The type to filter by.
     * @return A new Trades object containing the filtered trades.
     */
    public Trades filterByType(String type)
    {
        //To implement

        return null;
    }
}
