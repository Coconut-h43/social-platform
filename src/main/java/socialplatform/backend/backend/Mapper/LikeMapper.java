package socialplatform.backend.backend.Mapper;

import socialplatform.backend.backend.model.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface LikeMapper {
    //基础CRUD
    int insert(Like like);
    int deleteByUserAndTarget(@Param("userId") Integer userId, @Param("targetId") Integer targetId, @Param("targetType") Integer targetType);  //取消点赞，type 1=文章, 2=评论
    boolean existsLike(@Param("userId") Integer userId, @Param("targetId") Integer targetId, @Param("targetType") Integer targetType);//检查
    int countByTarget(@Param("targetId") Integer targetId, @Param("targetType") Integer targetType);//获取点赞数

    //系统清理功能
    int deleteByTarget(@Param("targetId") Integer targetId, @Param("targetType") Integer targetType);//删除文章或评论下的所有点赞
    int deleteByUser(Integer userId);

    //管理员统计功能
    List<Map<String, Object>> selectHotArticles(@Param("limit") Integer limit);//获取热门文章
    List<Map<String, Object>> countLikesTrend(@Param("startDate") String startDate, @Param("endDate") String endDate);//点赞趋势统计

    //用户信息中心
    List<Map<String, Object>> selectReceivedLikes(@Param("userId") Integer userId);

}
