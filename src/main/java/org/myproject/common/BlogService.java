package org.myproject.common;

/**
 * BlogService接口，定义客户端可调用的博客相关方法
 */
public interface BlogService {
    /**
     * 根据博客ID获取博客信息
     *
     * @param id 博客ID
     * @return Blog对象
     */
    Blog getBlogById(Integer id);
}