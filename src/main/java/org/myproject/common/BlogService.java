package org.myproject.common;

/**
 * BlogService�ӿڣ�����ͻ��˿ɵ��õĲ�����ط���
 */
public interface BlogService {
    /**
     * ���ݲ���ID��ȡ������Ϣ
     *
     * @param id ����ID
     * @return Blog����
     */
    Blog getBlogById(Integer id);
}