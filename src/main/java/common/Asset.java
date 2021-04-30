package common;

/**
 * This class implements an asset.
 */
public class Asset implements java.io.Serializable {

    /* Fields */

    /**
     * Class version number.
     */
    private static final long serialVersionUID = 0;

    /**
     * The quantity of an asset possessed by a unit.
     */
    private int quantity;

    /**
     * An asset's name.
     */
    private String name;


    /* Constructors */

    /**
     * Client-side constructor to create a new asset object when a new asset is created.
     *
     * @param name An asset's name.
     */
    public Asset(String name) {
        quantity = 0;
        this.name = name;
    }

    /**
     * Server-side constructor to create a new asset object when an asset is retrieved from the database.
     *
     * @param quantity The quantity of an asset possessed by a unit.
     * @param name An asset's name.
     */
    public Asset(int quantity, String name) {
        this.quantity = quantity;
        this.name = name;
    }
}
