package common.domain;

public class Asset extends Entity {
    private final String assetId;
    private final String assetName;

    public Asset(String assetId, String assetName) {
        this.assetId = assetId;
        this.assetName = assetName;
    }

    public String getAssetId() {
        return assetId;
    }

    public String getAssetName() {
        return assetName;
    }
}
