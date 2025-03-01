package org.myproject.server;

import org.myproject.common.User;
import org.myproject.common.UserService;

import java.util.Random;
import java.util.UUID;

/**
 * UserService �ľ���ʵ���࣬�ṩʵ�ʵ�ҵ���߼�
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUserByUserId(Integer id) {
        System.out.println("����ˣ����յ��ͻ��˲�ѯ�û�ID��" + id);
        // ģ�����ݿ��ѯ
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
        System.out.println("����ˣ��������ݳɹ���" + user);
        // ģ�����ݿ�������������1��ʾ�ɹ�
        return 1;
    }
}
