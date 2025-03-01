package org.myproject.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * RPC��Ӧ���󣬰����������õĽ����״̬��Ϣ
 */
@Data
@Builder
public class RPCResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    // ״̬�룬200��ʾ�ɹ���500��ʾʧ��
    private int code;
    // ״̬��Ϣ
    private String message;
    // ���ص�����
    private Object data;

    /**
     * �ɹ���Ӧ�Ŀ�ݷ���
     *
     * @param data ��������
     * @return RPCResponse����
     */
    public static RPCResponse success(Object data) {
        return RPCResponse.builder().code(200).data(data).build();
    }

    /**
     * ʧ����Ӧ�Ŀ�ݷ���
     *
     * @return RPCResponse����
     */
    public static RPCResponse fail() {
        return RPCResponse.builder().code(500).message("��������������").build();
    }
}
