package org.myproject.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * RPC������󣬰��������������Ϣ
 */
@Data
@Builder
public class RPCRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    // �����������ͻ���ֻ֪���ӿ������ڷ�������ýӿ���ָ��ʵ����
    private String interfaceName;
    // ������
    private String methodName;
    // �����б�
    private Object[] params;
    // ��������
    private Class<?>[] paramsTypes;
}
