package socialplatform.backend.backend.service;

import socialplatform.backend.backend.service.dto.user.*;
import socialplatform.backend.backend.service.vo.*;
import socialplatform.backend.backend.model.User;
import java.util.List;

public interface UserService {
    // 注册与登录
    void register(RegisterDTO register);
    LoginResult login(LoginDTO loginDTO);
    void logout(Integer id);

    // 个人信息管理
    User getUser(Integer id);
    void updateProfile(Integer id, UpdateProfileDTO updateProfileDTO);
    void updatePassword(Integer id, UpdatePasswordDTO passwordDTO);

    // 用户管理（管理员）
    PageResult<UserAdminVO> getAllUsers(Integer page, Integer pageSize);
    PageResult<UserAdminVO> getUsersByCondition(UserQueryDTO queryDTO);
    void updateUserStatus(Integer id, Integer status);
    void resetUserPassword(Integer id);
    void unlockUser(Integer id);
    void updateUserRole(Integer id, Integer roleId);
    void batchUpdateUserStatus(List<Integer> ids, Integer status);
    UserStatsVO getUserStats(Integer id);

    // 公开信息
    UserPublicVO getUserPublic(Integer id);
    PageResult<UserAdminStatsVO> getAllUsersWithStats(Integer page, Integer pageSize);
}