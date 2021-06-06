package common.dto;

/**
 * This class represents the delete asset DTO
 */
public class DeleteAssetDTO extends DTO {
    private final String assetId;

    /**
     * Create delete asset DTO
     *
     * @param assetId String of asset ID
     */
    public DeleteAssetDTO(String assetId) {
        this.assetId = assetId;
    }

    /**
     * Get asset ID
     *
     * @return String of asset ID
     */
    public String getAssetId() {
        return assetId;
    }
}
