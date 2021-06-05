package common.dto;

public class NewUnitDTO extends DTO {
    private final String unitName;

    public NewUnitDTO(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitName() {
        return unitName;
    }
}
