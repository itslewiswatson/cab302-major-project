package common.dto;

public class UpdateCreditsDTO extends DTO {
    private final String unitId;
    private final int newCredits;

    public UpdateCreditsDTO(String unitId, int newCredits) {
        this.unitId = unitId;
        this.newCredits = newCredits;
    }

    public String getUnitId() {
        return unitId;
    }

    public int getNewCredits() {
        return newCredits;
    }
}
