package socialplatform.backend.backend.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import socialplatform.backend.backend.model.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    //用户基础CRUD：增删改查
    int insert(User user);//增：插入新用户
    User selectById(Integer id);//根据ID查询
    User selectByUsername(String username);//根据用户名查
    User selectByEmail(String email);//根据邮箱查
    int update(User user);//更新用户信息
    int deleteById(Integer id);//删除用户，更新用户状态为禁用

    //用户状态与安全
    int updateStatus(@Param("id") Integer id,@Param("status") Integer status);//更新用户状态
    int updatePassword(@Param("id") Integer id,@Param("password") String password);
    int updateLoginFailure(@Param("id") Integer id,@Param("login_fail_count") Integer login_fail_count,@Param("accountLockedUntil") Date accountLockedUntil);
    int resetLoginFailure(Integer id);//解除用户锁定，重置登录失败次数
    int updateLastPasswordChanged(Integer id);
    List<User> selectByKeyword(@Param("keyword") String keyword);

    //查询操作
    List<User> selectAll();//查询所有用户
    List<User> selectByPage(@Param("offset") int offset,@Param("pageSize") int pageSize);

    List<User> selectByCondition(Map<String, Object> params);
    int countUsers();
    int countByCondition(Map<String, Object> params);
    List<User> selectByIds(@Param("ids") List<Integer> ids);

    //管理员操作
    List<Map<String,Object>>  selectUserWithStats(Map<String, Object> params);//查询用户列表，用于管理后台
    int updateRole(@Param("id") Integer id,@Param("role_id") Integer role_id);
    int batchUpdateStats(@Param("ids") List<Integer> ids,@Param("status") Integer status);

    //存储过程调用
    Map<String,Object> getUserArticleStats(Integer id);//获取用户文章统计数据
    Map<String,Object> getUserInteractionStats(Integer id);//获取用户互动统计数据
}
