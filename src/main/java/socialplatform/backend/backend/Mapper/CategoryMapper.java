package socialplatform.backend.backend.Mapper;

import org.apache.ibatis.annotations.Mapper;
import socialplatform.backend.backend.model.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    //管理员操作
    //基础CRUD
    int deleteById(Integer id);
    int insert(Category record);
    int update(Category record);
    Category selectById(Integer id);
    //查询操作(查询所有分类，含分页功能)
    List<Category> selectAll();
    //检查分类是否被使用
    boolean isCategoryUsed(Integer categoryId);

    //用户操作
    List<Category> selectAvailableCategories();//查询可用分类


}
