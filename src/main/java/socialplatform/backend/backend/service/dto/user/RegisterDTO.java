package socialplatform.backend.backend.service.dto.user;

import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String email;
    private String avatar;
}
