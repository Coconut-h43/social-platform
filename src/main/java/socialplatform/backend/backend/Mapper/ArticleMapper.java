package socialplatform.backend.backend.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import socialplatform.backend.backend.model.Article;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleMapper {
    //文章基础CRUD
    int insert(Article article);//增
    int deleteById(Integer id);//按照文章ID删除
    int update(Article article);//改：更新文章信息
    Article selectById(Integer id);//查：按照文章ID查询
    List<Article> searchByTitle(@Param("keyword") String keyword);//按文章标题查找
    List<Article> selectByCategoryId(Integer categoryId);//按照分类查找

    //文章状态
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    //文章查询操作
    List<Article> selectByPage(@Param("offset") int offset, @Param("pageSize") int pageSize);//分页查询
    List<Article> selectByCondition(Map<String, Object> params);//多条件动态查询
    List<Article> selectPublishedByCategory(@Param("categoryId") Integer categoryId, @Param("offset") int offset, @Param("pageSize") int pageSize);//按分类查找已发布文章

    List<Article> selectPublishedByAuthorId(@Param("authorId") Integer authorId, @Param("offset") int offset, @Param("pageSize") int pageSize);//按作者查找已发布文章
    List<Article> selectByAuthorId(@Param("authorId") Integer authorId);//查找某用户所有文章
    List<Article> selectDraftsByAuthorIdPage(@Param("authorId") Integer authorId, @Param("offset") int offset, @Param("pageSize") int pageSize);//分页查询草稿文章

    int updateTopStatus(@Param("id") Integer id, @Param("istop") Integer isTop);
    List<Article> selectTopArticles();
    List<Article> selectDraftsByAuthorId(@Param("authorId") Integer authorId);//查询用户的草稿文章

    //统计操作
    int countArticles();
    int countByCondition(Map<String, Object> params);
    int updateViewCount(Integer id);

    //排序操作
    List<Article> selectHotArticles(@Param("limit") Integer limit);//按文章热度排序
    List<Article> selectOrderByTitle(@Param("offset") int offset, @Param("pageSize") int pageSize);//按文章标题首字母排序
    List<Article> selectOrderByUpdateTime(@Param("offset") int offset, @Param("pageSize") int pageSize);//按更新时间排序

    //批量操作
    int batchUpdateStatus(@Param("ids") List<Integer> ids, @Param("status") Integer status);
    List<Article> selectByIds(@Param("ids") List<Integer> ids);

    //存储过程调用操作
    void incrementViewCount(Integer id);//增加文章浏览量
    Map<String, Object> getArticleStats(Integer id);//获取文章统计数据
}
