package common.dto;

/**
 * This class represents the update credits DTO
 */
public class UpdateCreditsDTO extends DTO {
    private final String unitId;
    private final int newCredits;

    /**
     * Create update credits DTO
     *
     * @param unitId String of unit ID
     * @param newCredits Int of new credits
     */
    public UpdateCreditsDTO(String unitId, int newCredits) {
        this.unitId = unitId;
        this.newCredits = newCredits;
    }

    /**
     * Get unit ID
     *
     * @return String of unit ID
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * Get new credits
     *
     * @return Integer of new credits
     */
    public int getNewCredits() {
        return newCredits;
    }
}
