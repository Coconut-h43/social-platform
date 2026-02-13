package socialplatform.backend.backend.Mapper;

import socialplatform.backend.backend.model.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FavoriteMapper {
    //基础CRUD
    int insert(Favorite favorite);
    int deleteByUserIdAndArticleId(@Param("userId") Integer userId, @Param("articleId") Integer articleId);//取消收藏
    Favorite select(@Param("id")  Integer id);

    //查询用户收藏的文章
    List<Favorite> selectByUserId(@Param("userId") Integer userId);
    List<Map<String, Object>> selectUserFavoritesWithArticleInfo(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
    List<Favorite> selectByArticleId(@Param("articleId") Integer articleId,@Param("offset")  Integer offset, @Param("pageSize") Integer pageSize);

    //检查是否已收藏
    boolean existsByUserAndArticle(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    //统计相关
    int countByUserId(@Param("userId") Integer userId);//统计用户收藏数量
    int countByArticleId(@Param("articleId") Integer articleId);//统计文章被收藏数
    Map<String, Object> selectFavoriteStats();  // 获取收藏统计概览
    List<Map<String, Object>> selectMostFavoritedArticles(@Param("limit") Integer limit);  // 最受欢迎文章
    List<Map<String, Object>> selectMostActiveUsers(@Param("limit") Integer limit);  // 最活跃收藏用户
    List<Map<String, Object>> selectFavoriteTrend(@Param("days") Integer days);  // 收藏趋势（按天统计）

    //批量管理
    int deleteByArticleIds(@Param("articleIds") List<Integer> articleIds);  // 批量删除文章相关的收藏
    int deleteByUserIds(@Param("userIds") List<Integer> userIds);  // 批量删除用户的收藏

    //异常检测
    List<Map<String, Object>> selectSuspiciousFavorites(@Param("threshold") Integer threshold);  // 可疑收藏行为

    //数据导出
    List<Map<String, Object>> selectAllForExport();  // 导出所有收藏数据

    //存储过程调用
    Map<String, Object> toggleFavorite(@Param("articleId") Integer articleId, @Param("userId") Integer userId);//切换文章收藏状态
    List<Map<String, Object>> selectRecentFavorites(@Param("userId") Integer userId, @Param("limit") Integer limit);//获取用户最近收藏

}

