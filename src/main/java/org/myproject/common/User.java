package org.myproject.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * �û�ʵ���࣬������ͻ��˺ͷ����
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String userName;
    private Boolean sex;
}

//Lombok ע�⣺
//@Data���Զ����� getters��setters��toString��equals �� hashCode ������
//@Builder��Ϊ�����ɽ�����ģʽ��ʵ�֣��򻯶��󴴽���
//@NoArgsConstructor �� @AllArgsConstructor�������޲κ�ȫ�ι��췽����
//        Serializable�ӿڣ�ȷ������������л����Ա�ͨ�����紫�䡣