package mk.ukim.finki.wp.repository;

import mk.ukim.finki.wp.model.Follow;
import mk.ukim.finki.wp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowing(User follower, User following);
    int countByFollower(User user);
    int countByFollowing(User user);
    Follow findByFollowerAndFollowing(User follower, User following);
    List<Follow> findByFollower(User following);
}
