package socialplatform.backend.backend.Mapper;

import socialplatform.backend.backend.model.ArticleTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleTagMapper {
    //关联管理
    int insert(@Param("articleId") Integer articleId, @Param("tagId") Integer tagId);
    int batchInsert(@Param("articleId") Integer articleId, @Param("tagIds") List<Integer> tagIds);
    List<ArticleTag> selectByArticleId(@Param("articleId") Integer articleId);
    List<ArticleTag> selectByTagId(@Param("tagId") Integer tagId);
    boolean exists(@Param("articleId") Integer articleId, @Param("tagId") Integer tagId);//检查关联是否存在
    int deleteByArticleId(@Param("articleId") Integer articleId);
    int deleteByTagId(@Param("tagId") Integer tagId);
    int batchDeleteByTagIds(@Param("tagIds") List<Integer> tagIds);
    //查询管理
    List<Integer> selectArticleIdByTagId(@Param("tagId") Integer tagId);//查询文章的所有标签
    List<Integer> selectTagIdByArticleId(@Param("articleId") Integer articleId);//查询包含该标签的所有文章
    int countByArticleId(@Param("articleId") Integer articleId);//查询文章的标签数量
    int countByTagId(@Param("tagId") Integer tagId);//查询使用某个标签的文章数量
    Integer selectIdByArticleAndTag(@Param("articleId") Integer articleId, @Param("tagId") Integer tagId);


}
