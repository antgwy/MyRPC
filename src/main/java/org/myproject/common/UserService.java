package org.myproject.common;

/**
 * 用户服务接口，定义客户端可调用的方法
 */
public interface UserService {
    /**
     * 根据用户ID获取用户信息
     *
     * @param id 用户ID
     * @return User对象
     */
    User getUserByUserId(Integer id);

    /**
     * 插入用户数据
     *
     * @param user 用户对象
     * @return 操作结果（例如，1表示成功）
     */
    Integer insertUserId(User user);
}
