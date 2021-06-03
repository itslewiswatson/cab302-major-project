package common.dto;

public class DeleteUserDTO extends DTO {
    private final String userId;

    public DeleteUserDTO(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
