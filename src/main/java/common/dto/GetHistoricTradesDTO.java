package common.dto;

import org.jetbrains.annotations.Nullable;

public class GetHistoricTradesDTO extends DTO {
    private final @Nullable String unitId;

    public GetHistoricTradesDTO(@Nullable String unitId) {
        this.unitId = unitId;
    }

    public @Nullable String getUnitId() {
        return unitId;
    }
}
