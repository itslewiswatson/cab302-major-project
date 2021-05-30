package common.dto;

import org.jetbrains.annotations.Nullable;

public class GetUnitsDTO extends DTO {
    private final @Nullable
    String userId;

    public GetUnitsDTO(@Nullable String userId) {
        this.userId = userId;
    }

    public @Nullable String getUserId() {
        return userId;
    }
}
