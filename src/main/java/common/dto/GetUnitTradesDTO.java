package common.dto;

public class GetUnitTradesDTO extends DTO {
    private final String unitId;

    public GetUnitTradesDTO(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitId() {
        return unitId;
    }
}
