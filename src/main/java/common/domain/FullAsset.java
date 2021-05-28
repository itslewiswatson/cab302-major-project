package common.domain;

import java.time.LocalDate;

public class FullAsset extends Asset {
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
}
