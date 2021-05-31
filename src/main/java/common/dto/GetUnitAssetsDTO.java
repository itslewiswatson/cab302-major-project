package common.dto;

public class GetUnitAssetsDTO extends DTO {
    private final String unitId;

    public GetUnitAssetsDTO(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitId() {
        return unitId;
    }
}
