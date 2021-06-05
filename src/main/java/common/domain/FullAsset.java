package common.domain;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public class FullAsset extends Asset implements Comparable<FullAsset> {
    private final LocalDate dateAdded;
    private final int amount;

    public FullAsset(String assetId, String assetName, LocalDate dateAdded, int amount) {
        super(assetId, assetName);
        this.dateAdded = dateAdded;
        this.amount = amount;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public int compareTo(@NotNull FullAsset otherFullAsset) {
        return this.getAssetName().toLowerCase().compareTo(otherFullAsset.getAssetName().toLowerCase());
    }
}
