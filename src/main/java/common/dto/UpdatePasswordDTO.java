package common.dto;

public class UpdatePasswordDTO extends DTO {
    private final String userId;
    private final String oldPassword;
    private final String newPassword;

    public UpdatePasswordDTO(String userId, String oldPassword, String newPassword) {
        this.userId = userId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getUserId() {
        return userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
