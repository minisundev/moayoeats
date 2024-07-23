package com.moayo.moayoeats.backend.domain.post.repository;

import com.moayo.moayoeats.backend.domain.post.entity.CategoryEnum;
import com.moayo.moayoeats.backend.domain.post.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long postId);

    List<Post> findAll();

    Page<Post> findAllByCuisineEquals(Pageable pageable,String category);

    Optional<List<Post>> findPostByStoreContaining(String store);

    Page<Post> findPostByStoreContaining(Pageable pageable, String store);

}
