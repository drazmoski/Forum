package mk.ukim.finki.wp.repository;


import mk.ukim.finki.wp.model.LikePost;
import mk.ukim.finki.wp.model.Post;
import mk.ukim.finki.wp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    boolean existsByUserAndPost(User user, Post post);
    int countByPost(Post post);
    List<LikePost> findByUser(User user);
    LikePost findByUserAndPost(User user, Post post);
}
