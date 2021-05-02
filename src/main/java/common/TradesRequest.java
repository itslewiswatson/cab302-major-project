package common;

/**
 * This class represents a request for trades.
 *
 * @author Mitchell Egan
 */
public class TradesRequest implements java.io.Serializable {

    /**
     * The class' version number.
     */
    private static final long serialVersionUID = 0;

    /**
     * An indication that the request is for completed trades.
     */
    private final boolean completed;

    /**
     * The name of the asset that the trades are being requested for.
     */
    private final String asset;

    /**
     * The name of the organisational unit that the trades are being requested for.
     */
    private final String unit;

    /**
     * Creates a TradesRequest object when all uncompleted trades are to be requested.
     */
    public TradesRequest() {
        completed = false;
        asset = "";
        unit = "";
    }

    /**
     * Creates a TradesRequest object when all completed trades are to be requested.
     *
     * @param completed An indication that the request is for completed trades.
     * @param asset The name of the asset that the trades are being requested for.
     */
    public TradesRequest(boolean completed, String asset)
    {
        this.completed = completed;
        this.asset = asset;
        unit = "";
    }

    /**
     * Creates a TradesRequest object when all trades belonging to a single organisational unit are to be requested.
     *
     * @param unit The name of the organisational unit that the trades are being requested for.
     */
    public TradesRequest(String unit) {
        asset = "";
        completed = false;
        this.unit = unit;
    }

    /**
     * Gets the trades request's completed field.
     *
     * @return An indication that the request is for completed trades.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Get the trades request's asset field.
     *
     * @return The name of the asset that the trades are being requested for.
     */
    public String getAsset() {
        return asset;
    }

    /**
     * Get's the trades request's unit field.
     *
     * @return The name of the organisational unit that the trades are being requested for.
     */
    public String getUnit() {
        return unit;
    }
}
