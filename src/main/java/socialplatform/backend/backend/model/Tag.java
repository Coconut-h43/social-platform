package socialplatform.backend.backend.model;

import lombok.Data ;
import java.util.Date;

@Data
public class Tag {
    private Integer id;
    private String name;//标签名称
    private String color;//标签颜色
    private Date updateTime;
    private Date createTime;
}

