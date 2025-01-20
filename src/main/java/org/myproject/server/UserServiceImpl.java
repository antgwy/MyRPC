package org.myproject.server;

import org.myproject.common.User;
import org.myproject.common.UserService;

import java.util.Random;
import java.util.UUID;

/**
 * UserService 的具体实现类
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUserByUserId(Integer id) {
        System.out.println("服务端：接收到客户端查询用户ID：" + id);
        // 模拟数据库查询
        Random random = new Random();
        User user = User.builder()
                .id(id)
                .userName(UUID.randomUUID().toString())
                .sex(random.nextBoolean())
                .build();
        return user;
    }
}
