package socialplatform.backend.backend.service.dto.user;

import lombok.Data;
import java.util.Date;

@Data
public class UserQueryDTO {
    private String keyword;
    private Integer status;
    private Integer roleId;
    private Date startTime;
    private Date endTime;
    private Integer page;
    private Integer pageSize;
    private String orderBy;
    private String orderDirection;
}
