package com.example.blogproject.post.repository;


import com.example.blogproject.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByBlogId(Long blogId);

    List<Post> findAllByCategoryId(Long categoryId);
}
