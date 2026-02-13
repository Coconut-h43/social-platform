package socialplatform.backend.backend.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import socialplatform.backend.backend.model.Tag;

import java.util.Date;
import java.util.List;

@Mapper
public interface TagMapper {
    //管理员权限：基础CRUD
    int insert(Tag tag);
    int deleteById(Integer id);
    int updateTag(Tag tag);
    Tag selectById(Integer id);
    List<Tag> selectByCreateTime(@Param("create_time") Date create_time);
    List<Tag> selectByUpdateTime(@Param("update_time") Date update_time);

    //查询操作
    int count();
    List<Tag> selectByPage(@Param("offset") int offset, @Param("PageSize") int PageSize, @Param("orderBy") String orderBy);//分页查询标签
    List<Tag> selectByKeyword(@Param("keyword") String keyword);//关键词搜索标签
    List<Tag> selectByUsageCount(@Param("minUsageCount") Integer minUsageCount);    // 按使用次数筛选（大于等于50次）

    //标签的统计与展示
    List<Tag> getHotTag(@Param("limit") Integer limit);//获取热门标签
    List<Tag> selectAll();
    List<Tag> selectAllOrderByName();//获取所有标签列表，按照名字排序
    Integer selectUsageCountById(Integer tagId);//获取标签使用次数

    //标签唯一性检验
    Tag selectByName(String name);
    boolean existsNameExcludeSelf(@Param("name") String name, @Param("excludeId") Integer excludeId);//检查标签是否存在
}
