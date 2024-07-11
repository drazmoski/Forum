package mk.ukim.finki.wp.service;


import mk.ukim.finki.wp.model.Post;
import mk.ukim.finki.wp.model.User;

import java.util.List;

public interface PostService {
    Post createPost(User user, String content, String image);
    List<Post> findAll();
    Post findById(Long id);
    List<Post> findByUser(User user);
    void deletePost(Post post);
    void updatePost(Post post, String content, String image);
    List<Post> findPostsByUsersFollowing(User user);
}