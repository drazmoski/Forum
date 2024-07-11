package mk.ukim.finki.wp.repository;

import mk.ukim.finki.wp.model.Post;
import mk.ukim.finki.wp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
    List<Post> findAllByOrderByTimestampDesc();
}