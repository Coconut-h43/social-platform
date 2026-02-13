package socialplatform.backend.backend.Mapper;

import socialplatform.backend.backend.model.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    //基础CRUD
    int deleteById(Integer commentId);//逻辑删除，设置status=0
    int delete(Integer commentId);//管理员删除权限：物理删除
    int insert(Comment record);
    int update(Comment record);
    Comment selectById(Integer commentId);
    List<Comment> selectByUserId(Integer userId);
    List<Comment> selectByParentId(Integer parentId);

    //查询操作
    List<Comment> selectCommentByArticleId(Integer articleId,int offset,int limit);
    List<Comment> selectCommentByUserId(Integer userId,int offset,int limit);
    List<Comment> selectCommentReceived(Integer userId,int offset,int limit);
    List<Comment> selectCommentReplies(Integer parentId,int offset,int limit);//嵌套评论展示

    //评论归属认证
    boolean isCommentBelongsToUser(Integer commentId, Integer userId);
    boolean isCommentBelongsToArticle(Integer commentId, Integer articleId);

    //统计相关操作
    int countCommentByArticleId(Integer articleId);
    int countCommentByUserId(Integer userId);
    int countCommentReceived(Integer userId);
    int countCommentReplies(Integer parentId);

    //管理员禁用评论功能
    List<Comment> selectCommentsByKeyword(String keyword, Integer offset, Integer limit);
    int disableCommentsByKeyword(String keyword);
    int restoreById(Integer commentId);

    //定位跳转
    Comment selectCommentWithLocation(Integer commentId);
}
