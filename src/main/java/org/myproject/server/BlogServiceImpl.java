package org.myproject.server;

import org.myproject.common.Blog;
import org.myproject.common.BlogService;

import java.util.UUID;

/**
 * BlogService �ľ���ʵ���࣬�ṩʵ�ʵ�ҵ���߼�
 */
public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder()
                .id(id)
                .useId(22)
                .title("�ҵĲ��� " + UUID.randomUUID().toString())
                .build();
        System.out.println("����ˣ���ѯ������ID " + id + "���������ݣ�" + blog);
        return blog;
    }
}
