package org.myproject.server;

import org.myproject.common.User;
import org.myproject.common.UserService;

import java.util.Random;
import java.util.UUID;

/**
 * UserService 的具体实现类，提供实际的业务逻辑
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

    @Override
    public Integer insertUserId(User user) {
        System.out.println("服务端：插入数据成功：" + user);
        // 模拟数据库插入操作，返回1表示成功
        return 1;
    }
}
