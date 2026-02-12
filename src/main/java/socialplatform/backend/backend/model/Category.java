package socialplatform.backend.backend.model;

import lombok.Data;
import java.util.Date;

@Data
public class Category {
    private Integer id;// 分类ID
    private String name; //分类名称
    private Date updateTime;//更新时间
    private Date createTime;//创建时间
}
