package mk.ukim.finki.wp.service.impl;

import mk.ukim.finki.wp.model.Post;
import mk.ukim.finki.wp.model.User;
import mk.ukim.finki.wp.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.wp.repository.PostRepository;
import mk.ukim.finki.wp.service.FollowService;
import mk.ukim.finki.wp.service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final FollowService followService;

    public PostServiceImpl(PostRepository postRepository, FollowService followService) {
        this.postRepository = postRepository;
        this.followService = followService;
    }

    @Override
    public Post createPost(User user, String content, String image) {
        Post post = new Post();
        post.setUser(user);
        post.setContent(content);
        post.setImage(image);
        post.setTimestamp(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAllByOrderByTimestampDesc();
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(InvalidArgumentsException::new);
    }

    @Override
    public List<Post> findByUser(User user) {
        return postRepository.findByUser(user);
    }

    @Override
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    public void updatePost(Post post, String content, String image) {
        post.setContent(content);
        post.setImage(image);
        postRepository.save(post);
    }

    @Override
    public List<Post> findPostsByUsersFollowing(User user) {
        List<User> following = followService.getFollowing(user);
        return postRepository.findAllByOrderByTimestampDesc().stream()
                .filter(post -> following.contains(post.getUser()))
                .collect(Collectors.toList());
    }
}