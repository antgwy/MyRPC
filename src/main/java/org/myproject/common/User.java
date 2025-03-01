package org.myproject.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户实体类，共享给客户端和服务端
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

//Lombok 注解：
//@Data：自动生成 getters、setters、toString、equals 和 hashCode 方法。
//@Builder：为类生成建造者模式的实现，简化对象创建。
//@NoArgsConstructor 和 @AllArgsConstructor：生成无参和全参构造方法。
//        Serializable接口：确保对象可以序列化，以便通过网络传输。