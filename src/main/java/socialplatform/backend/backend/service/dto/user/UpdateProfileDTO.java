package socialplatform.backend.backend.service.dto.user;

import lombok.Data;

@Data
public class UpdateProfileDTO {
    private String username;
    private String email;
    private String avatar;
}
