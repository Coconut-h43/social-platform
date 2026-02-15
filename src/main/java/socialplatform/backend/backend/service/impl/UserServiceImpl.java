package socialplatform.backend.backend.service.impl;

import cn.hutool.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import socialplatform.backend.backend.Mapper.UserMapper;
import socialplatform.backend.backend.model.User;
import socialplatform.backend.backend.service.*;
import socialplatform.backend.backend.service.dto.user.*;
import socialplatform.backend.backend.service.exception.BusinessException;
import socialplatform.backend.backend.service.vo.*;
import socialplatform.backend.backend.utils.JwtUtil;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public void register(RegisterDTO register){
        //参数异常
        if(!StringUtils.hasText(register.getUsername())){
            throw new BusinessException("用户名不能为空");
        }
        if(!StringUtils.hasText(register.getPassword())){
            throw new BusinessException(("密码不能为空"));
        }
        if(!StringUtils.hasText(register.getEmail())){
            throw new BusinessException("邮箱不能为空");
        }
        //用户名异常
        User existUser = userMapper.selectByUsername(register.getUsername());
        if(existUser != null) {
            throw new BusinessException("用户名已存在");
        }
        //邮箱异常
        User existEmail = userMapper.selectByEmail(register.getEmail());
        if(existEmail != null) {
            throw new BusinessException("邮箱已被注册");
        }
        //密码强度检查
        String password = register.getPassword();
        if(password.length() < 8 || !password.matches(".*[A-Za-z].*") || !password.matches(".*\\d.*")) {
            throw new BusinessException("密码必须为8位数以上且包含字母和数字");
        }
        //创建用户
        User user = new User();
        user.setUsername(register.getUsername());
        user.setEmail(register.getEmail());
        user.setPassword(password);
        user.setAvatar(register.getAvatar() != null ?register.getAvatar()
                : "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");//注册后用户默认头像
        user.setStatus(1);//状态正常
        user.setRoleId(2);//普通用户
        user.setLoginFailCount(0);
        userMapper.insert(user);

    }

    @Override
    public LoginResult login(LoginDTO loginDTO) {
        //参数异常
        if(!StringUtils.hasText(loginDTO.getUsername())){
            throw new BusinessException("用户名不能为空");
        }
        if(!StringUtils.hasText(loginDTO.getPassword())){
            throw new BusinessException(("密码不能为空"));
        }
        //用户异常
        User user = userMapper.selectByUsername(loginDTO.getUsername());
        if(user == null){
            throw new BusinessException("用户名或密码错误");
        }
        //用户状态异常
        if (user.getStatus() == 0) {
            throw new BusinessException("账户已被封禁");
        }
        //检查是否被锁定
        if (user.getAccountLockedUntil() != null && !user.getAccountLockedUntil().after(new Date())) {
            long minutes = (user.getAccountLockedUntil().getTime() - System.currentTimeMillis()) / 6000;
            throw new BusinessException("账户已被锁定" + minutes + "分钟后再试");
        }
        //密码错误
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            // 失败次数+1
            int failCount = user.getLoginFailCount() == null ? 1 : user.getLoginFailCount() + 1;
            Date lockedUntil = null;

            // 根据设计文档：失败超过3次自动锁定
            if (failCount >= 3) {
                // 锁定30分钟
                lockedUntil = new Date(System.currentTimeMillis() + 30 * 60 * 1000);
            }

            userMapper.updateLoginFailure(user.getId(), failCount, lockedUntil);

            if (failCount >= 3) {
                throw new BusinessException("密码错误过多，账户已锁定30分钟");
            } else {
                throw new BusinessException("密码错误，还剩" + (3 - failCount) + "次机会");
            }
        }
        //登录成功
        userMapper.resetLoginFailure(user.getId());
        //生成JWT token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRoleId());

        LoginResult result = new LoginResult();
        result.setToken(token);
        result.setId(user.getId());
        result.setUsername(user.getUsername());
        result.setRoleId(user.getRoleId());
        result.setAvatar(user.getAvatar());

        return result;
    }

    @Override
    public void logout(Integer id) {}

    @Override
    public User getUser(Integer id) {
        if(id == null){
            throw new BusinessException("用户ID不能为空");
        }
        User user = userMapper.selectById(id);
        if(user == null){
            throw new BusinessException("用户不存在");
        }
        return user;
    }

    @Override
    @Transactional
    public void updateProfile(Integer id, UpdateProfileDTO updateProfileDTO) {
        User user = userMapper.selectById(id);
        if(user == null){
            throw new BusinessException("用户不存在");
        }
        User updateUser = new User();
        updateUser.setId(id);
        //修改用户名
        if (StringUtils.hasText(updateProfileDTO.getUsername()) && !updateProfileDTO.getUsername().equals(user.getUsername())) {
            User existUser = userMapper.selectByUsername(updateProfileDTO.getUsername());
            if(existUser != null && !existUser.getId().equals(user.getId())){
                throw new BusinessException("用户名已存在");
            }
            updateUser.setUsername(updateProfileDTO.getUsername());
        }
        //修改邮箱
        if (StringUtils.hasText(updateProfileDTO.getEmail())  && !updateProfileDTO.getEmail().equals(user.getEmail())) {
            User existEmail = userMapper.selectByEmail(updateProfileDTO.getEmail());
            if(existEmail != null && !existEmail.getId().equals(user.getId())){
                throw new BusinessException("邮箱已被注册");
            }
            updateUser.setEmail(updateProfileDTO.getEmail());
        }
        //修改头像
        if (StringUtils.hasText(updateProfileDTO.getAvatar())) {
            updateUser.setAvatar(updateProfileDTO.getAvatar());
        }
        //对修改字段执行更新
        if (updateUser.getUsername() != null || updateUser.getEmail() != null || updateUser.getAvatar() != null) {
            userMapper.update(updateUser);
        }
    }

    @Override
    @Transactional
    public void updatePassword(Integer id, UpdatePasswordDTO passwordDTO) {
        // 参数校验
        if (!StringUtils.hasText(passwordDTO.getOldPassword()) ||
                !StringUtils.hasText(passwordDTO.getNewPassword())) {
            throw new BusinessException("旧密码和新密码不能为空");
        }
        User user = userMapper.selectById(id);
        if(user == null){
            throw new BusinessException("用户不存在");
        }
        // 验证旧密码
        if (!passwordEncoder.matches(passwordDTO.getOldPassword(), user.getPassword())) {
            throw new BusinessException("旧密码错误");
        }
        // 新密码强度校验
        String newPassword = passwordDTO.getNewPassword();
        if (newPassword.length() < 8 ||
                !newPassword.matches(".*[A-Za-z].*") ||
                !newPassword.matches(".*\\d.*")) {
            throw new BusinessException("密码必须为8位以上且包含字母和数字");
        }
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new BusinessException("新密码不能与旧密码相同");
        }
        userMapper.updatePassword(user.getId(), newPassword);
        userMapper.update(user);
    }

    @Override
    public PageResult<UserAdminVO> getAllUsers(Integer page, Integer pageSize) {
        // 默认分页参数
        if (page == null || page < 1) page = 1;
        if (pageSize == null || pageSize < 1) pageSize = 10;
        int offset = (page - 1) * pageSize;
        Map<String, Object> params = new HashMap<>();
        params.put("includeDeleted", false);  // 排除已删除用户
        params.put("offset", offset);
        params.put("pageSize", pageSize);
        List<User> users = userMapper.selectByCondition(params);
        Long totalCount = (long) userMapper.countByCondition(params);
        List<UserAdminVO> list = users.stream()
                .map(UserAdminVO::fromUser)
                .collect(Collectors.toList());

        return new PageResult<>(page, pageSize, totalCount, list);
    }

    @Override
    public PageResult<UserAdminVO> getUsersByCondition(UserQueryDTO queryDTO) {
        // 默认分页参数
        if (queryDTO.getPage() == null || queryDTO.getPage() < 1) queryDTO.setPage(1);
        if (queryDTO.getPageSize() == null || queryDTO.getPageSize() < 1) queryDTO.setPageSize(10);
        int offset = (queryDTO.getPage() - 1) * queryDTO.getPageSize();
        // 构建查询条件
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.hasText(queryDTO.getKeyword())) {
            params.put("keyword", queryDTO.getKeyword().trim());
        }
        if (queryDTO.getStatus() != null) {
            params.put("status", queryDTO.getStatus());
        }
        if (queryDTO.getRoleId() != null) {
            params.put("roleId", queryDTO.getRoleId());
        }
        if (queryDTO.getStartTime() != null) {
            params.put("startTime", queryDTO.getStartTime());
        }
        if (queryDTO.getEndTime() != null) {
            params.put("endTime", queryDTO.getEndTime());
        }
        params.put("includeDeleted", false);
        params.put("offset", offset);
        params.put("pageSize", queryDTO.getPageSize());
        // 排序
        if (StringUtils.hasText(queryDTO.getOrderBy())) {
            params.put("orderBy", queryDTO.getOrderBy());
            params.put("orderDirection",
                    "asc".equalsIgnoreCase(queryDTO.getOrderDirection()) ? "asc" : "desc");
        }
        List<User> users = userMapper.selectByCondition(params);
        Long totalCount = (long) userMapper.countByCondition(params);

        List<UserAdminVO> list = users.stream()
                .map(UserAdminVO::fromUser)
                .collect(Collectors.toList());
        return new PageResult<>(queryDTO.getPage(), queryDTO.getPageSize(), totalCount, list);
    }

    @Override
    @Transactional
    public void updateUserStatus(Integer id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("状态值不正确（0-禁用，1-正常）");
        }

        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        userMapper.updateStatus(id, status);
    }

    @Override
    @Transactional
    public void resetUserPassword(Integer id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        //重置为默认密码
        String defaultPassword = "12345678a"; //默认密码符合强度要求
        userMapper.updatePassword(id, passwordEncoder.encode(defaultPassword));
        userMapper.updateLastPasswordChanged(id);
    }

    @Override
    @Transactional
    public void unlockUser(Integer id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        userMapper.resetLoginFailure(id);
    }

    @Override
    public void updateUserRole(Integer id, Integer roleId) {
        if (roleId == null || (roleId != 1 && roleId != 2)) {
            throw new BusinessException("角色ID不正确（1-管理员，2-普通用户）");
        }
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        userMapper.updateRole(id, roleId);
    }

    @Override
    @Transactional
    public void batchUpdateUserStatus(List<Integer> ids, Integer status) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException("请选择要操作的用户");
        }
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("状态值不正确（0-禁用，1-正常）");
        }
        userMapper.batchUpdateStats(ids, status);
    }

    @Override
    public UserStatsVO getUserStats(Integer id) {
        // 调用存储过程获取文章统计数据
        Map<String, Object> articleStats = userMapper.getUserArticleStats(id);
        UserStatsVO stats = new UserStatsVO();
        stats.setId(id);
        if (articleStats != null) {
            stats.setArticleCount(((Long) articleStats.getOrDefault("total_articles", 0L)).intValue());
            stats.setPublishedCount(((Long) articleStats.getOrDefault("published_articles", 0L)).intValue());
            stats.setDraftCount(((Long) articleStats.getOrDefault("draft_articles", 0L)).intValue());
            stats.setTotalViews(((Long) articleStats.getOrDefault("total_views", 0L)).intValue());
            stats.setLastArticleTime((Date) articleStats.get("last_article_time"));
        }
        return stats;
    }

    @Override
    public UserPublicVO getUserPublic(Integer id) {
        User user = userMapper.selectById(id);
        if (user == null || user.getStatus() == 0) {
            throw new BusinessException("用户不存在");
        }
        return UserPublicVO.fromUser(user);
    }

    @Override
    public PageResult<UserAdminStatsVO> getAllUsersWithStats(Integer page, Integer pageSize) {
        // 默认分页参数
        if (page == null || page < 1) page = 1;
        if (pageSize == null || pageSize < 1) pageSize = 10;
        int offset = (page - 1) * pageSize;
        Map<String, Object> params = new HashMap<>();
        params.put("includeDeleted", false);
        params.put("offset", offset);
        params.put("pageSize", pageSize);
        params.put("orderBy", "create_time");
        params.put("orderDirection", "desc");
        // 调用Mapper的selectUserWithStats获取带统计的用户列表
        List<Map<String, Object>> statsList = userMapper.selectUserWithStats(params);
        // 获取总数
        Map<String, Object> countParams = new HashMap<>();
        countParams.put("includeDeleted", false);
        Long totalCount = (long) userMapper.countByCondition(countParams);
        List<UserAdminStatsVO> list = statsList.stream()
                .map(UserAdminStatsVO::fromMap)
                .collect(Collectors.toList());

        return new PageResult<>(page, pageSize, totalCount, list);
    }
}
