package common.dto;

/**
 * This class represents the add asset logic
 */
public class AddAssetDTO extends DTO {
    private final String assetName;

    /**
     * Creates an instance of add asset
     *
     * @param assetName The name of the asset
     */
    public AddAssetDTO(String assetName) {
        this.assetName = assetName;
    }

    /**
     * Gets the asset name
     *
     * @return String of the asset name
     */
    public String getAssetName() {
        return assetName;
    }
}
