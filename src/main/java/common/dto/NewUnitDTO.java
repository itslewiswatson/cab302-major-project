package common.dto;

import org.jetbrains.annotations.Nullable;

public class NewUnitDTO extends DTO {
    private String unitName;
    private Integer credits;

    public NewUnitDTO(@Nullable String unitName, @Nullable Integer credits) {
        this.unitName = unitName;
        this.credits = credits;
    }

    public String getUnitName() {
        return unitName;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }
}
