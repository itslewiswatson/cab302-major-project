package common.dto;

public class UpdatePasswordDTO extends DTO {
    private final String userId;
    private final String newPassword;

    public UpdatePasswordDTO(String userId, String newPassword) {
        this.userId = userId;
        this.newPassword = newPassword;
    }

    public String getUserId() {
        return userId;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
