package socialplatform.backend.backend.service.dto.user;

import lombok.Data;

@Data
public class UpdatePasswordDTO{
    private String oldPassword;
    private String newPassword;
}
