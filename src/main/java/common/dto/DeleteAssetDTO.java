package common.dto;

public class DeleteAssetDTO extends DTO {
    private final String assetId;

    public DeleteAssetDTO(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetId() {
        return assetId;
    }
}
