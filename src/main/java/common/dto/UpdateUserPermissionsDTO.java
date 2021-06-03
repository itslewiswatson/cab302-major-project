package common.dto;

public class UpdateUserPermissionsDTO extends DTO {
    private final String userId;
    private final boolean isAdmin;

    public UpdateUserPermissionsDTO(String userId, boolean isAdmin) {
        this.userId = userId;
        this.isAdmin = isAdmin;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
