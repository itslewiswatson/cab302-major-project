package common.dto;

public class GetUnitUsersDTO extends DTO {
    private final String unitId;

    public GetUnitUsersDTO(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitId() {
        return unitId;
    }
}
