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
}
