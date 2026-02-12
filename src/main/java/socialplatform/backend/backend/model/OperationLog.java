package socialplatform.backend.backend.model;

import lombok.Data ;
import java.util.Date;

@Data
public class OperationLog {
    private Integer id;
    private Integer userId;
    private String operationType;//操作类型
    private String operationTarget;//操作目标
    private String operationDetail;//操作详情
    private String ipAddress;//操作者ip
    private String userAgent;//用户代理
    private Integer status;//操作状态：0为失败，1为成功
    private Date createTime;
}
