package org.myproject.common;

/**
 * �û�����ӿڣ�����ͻ��˿ɵ��õķ���
 */
public interface UserService {
    /**
     * �����û�ID��ȡ�û���Ϣ
     *
     * @param id �û�ID
     * @return User����
     */
    User getUserByUserId(Integer id);

    /**
     * �����û�����
     *
     * @param user �û�����
     * @return ������������磬1��ʾ�ɹ���
     */
    Integer insertUserId(User user);
}
