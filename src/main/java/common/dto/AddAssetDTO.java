package common.dto;

public class AddAssetDTO extends DTO {
    private final String assetName;

    public AddAssetDTO(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetName() {
        return assetName;
    }
}
