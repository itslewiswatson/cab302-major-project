package common.dto;

public class RemoveUserFromUnitDTO extends DTO {
    private final String userId;
    private final String unitId;

    public RemoveUserFromUnitDTO(String userId, String unitId) {
        this.userId = userId;
        this.unitId = unitId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUnitId() {
        return unitId;
    }
}
