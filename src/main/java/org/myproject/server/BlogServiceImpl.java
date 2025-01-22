package org.myproject.server;

import org.myproject.common.Blog;
import org.myproject.common.BlogService;

import java.util.UUID;

/**
 * BlogService 的具体实现类，提供实际的业务逻辑
 */
public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder()
                .id(id)
                .useId(22)
                .title("我的博客 " + UUID.randomUUID().toString())
                .build();
        System.out.println("服务端：查询到博客ID " + id + "，博客内容：" + blog);
        return blog;
    }
}
