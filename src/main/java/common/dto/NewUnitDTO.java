package common.dto;

import org.jetbrains.annotations.Nullable;

/**
 * This class represents the new unit DTO
 */
public class NewUnitDTO extends DTO {
    private String unitName;
    private Integer credits;

    /**
     * Create new unit DTO
     *
     * @param unitName Name of unit
     * @param credits Number of credits
     */
    public NewUnitDTO(@Nullable String unitName, @Nullable Integer credits) {
        this.unitName = unitName;
        this.credits = credits;
    }

    /**
     * Get unit name
     *
     * @return String of unit name
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * Get credits
     *
     * @return Integer of credits
     */
    public Integer getCredits() {
        return credits;
    }

    /**
     * Sets the unit name
     *
     * @param unitName String of unit name
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /**
     * Sets the credits
     *
     * @param credits Int of credits
     */
    public void setCredits(Integer credits) {
        this.credits = credits;
    }
}
