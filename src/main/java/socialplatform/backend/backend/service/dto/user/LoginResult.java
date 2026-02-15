package socialplatform.backend.backend.service.dto.user;

import lombok.Data;

@Data
public class LoginResult{
    private Integer id;
    private String username;
    private String avatar;
    private Integer roleId;
    private String token;
}
