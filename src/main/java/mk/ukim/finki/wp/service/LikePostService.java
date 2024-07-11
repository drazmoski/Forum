package mk.ukim.finki.wp.service;

import mk.ukim.finki.wp.model.Post;
import mk.ukim.finki.wp.model.User;
import java.util.List;

public interface LikePostService {
    void likePost(User user, Post post);
    void unlikePost(User user, Post post);
    int countLikes(Post post);
    List<Long> findLikedPostIds(User user);
    boolean isLiked(User user, Post post);
}
