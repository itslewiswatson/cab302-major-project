package common;

/**
 * This class implements an organisational unit.
 */
public class Unit implements java.io.Serializable {

    /* Fields */

    /**
     * Class version number.
     */
    private static final long serialVersionUID = 0;

    /**
     * The assets possessed by a unit.
     */
    private Asset[] assets;

    /**
     * An indication that the unit's assets are to be updated in the database.
     */
    private boolean updateAssets;

    /**
     * An indication that the unit's credits are to be updated in the database.
     */
    private boolean updateCredits;

    /**
     * A unit's credit count.
     */
    private int credits;

    /**
     * A unit's name.
     */
    private String name;


    /* Constructors */

    /**
     * Client-side constructor to create a new unit object when a new unit is created.
     *
     * @param name A unit's name.
     */
    public Unit(String name) {
        assets = new Asset[0];
        updateAssets = false;
        updateCredits = false;
        credits = 0;
        this.name = name;
    }

    /**
     * Server-side constructor to create a new unit object when a unit is retrieved from the database.
     *
     * @param assets The assets possessed by a unit.
     * @param credits A unit's credit count.
     * @param name A unit's name.
     */
    public Unit(Asset[] assets, int credits, String name) {
        this.assets = assets;
        updateAssets = false;
        updateCredits = false;
        this.credits = credits;
        this.name = name;
    }


    /* Methods */

    /**
     * Set unit's assets field.
     *
     * @param assets The assets possessed by a unit.
     */
    public void setAssets(Asset[] assets) {
        this.assets = assets;
    }

    /**
     * Set unit's credit field.
     *
     * @param credits A unit's credit count.
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Set unit's updateAssets field.
     *
     * @param updateAssets An indication that the unit's assets are to be updated in the database.
     */
    public void setUpdateAssets(boolean updateAssets) {
        this.updateAssets = updateAssets;
    }

    /**
     * Set unit's updateCredits field.
     *
     * @param updateCredits An indication that the unit's credits are to be updated in the database.
     */
    public void setUpdateCredits(boolean updateCredits) {
        this.updateCredits = updateCredits;
    }
}
