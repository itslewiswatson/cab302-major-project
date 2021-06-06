package common.domain;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

/**
 * This class represents an asset with additional derived properties
 */
public class FullAsset extends Asset implements Comparable<FullAsset> {
    private final LocalDate dateAdded;
    private final int amount;

    /**
     * Creates an instance of the a full asset
     *
     * @param assetId   A string of the asset's ID
     * @param assetName A string of the asset's name
     * @param dateAdded The date the asset was added
     * @param amount    The amount in circulation
     */
    public FullAsset(String assetId, String assetName, LocalDate dateAdded, int amount) {
        super(assetId, assetName);
        this.dateAdded = dateAdded;
        this.amount = amount;
    }

    /**
     * Gets the date the asset was added
     *
     * @return The date when the asset was added
     */
    public LocalDate getDateAdded() {
        return dateAdded;
    }

    /**
     * Gets the amount of assets in circulation
     *
     * @return The amount of assets in circulation
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Compares two asset names
     *
     * @param otherFullAsset The other full asset to compare to
     * @return A boolean indicating whether the comparison holds true
     */
    @Override
    public int compareTo(@NotNull FullAsset otherFullAsset) {
        return this.getAssetName().toLowerCase().compareTo(otherFullAsset.getAssetName().toLowerCase());
    }
}
