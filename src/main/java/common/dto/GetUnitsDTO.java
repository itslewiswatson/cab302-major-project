package common.dto;

import org.jetbrains.annotations.Nullable;

/**
 * This class represents the get units DTO
 */
public class GetUnitsDTO extends DTO {
    private final @Nullable
    String userId;

    /**
     * Create get units DTO
     *
     * @param userId Optional string of user ID
     */
    public GetUnitsDTO(@Nullable String userId) {
        this.userId = userId;
    }

    /**
     * Get user ID
     *
     * @return Null or a string of user ID
     */
    public @Nullable String getUserId() {
        return userId;
    }
}
