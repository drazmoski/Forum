package mk.ukim.finki.wp.web.controller;

import mk.ukim.finki.wp.model.Post;
import mk.ukim.finki.wp.model.User;
import mk.ukim.finki.wp.service.LikePostService;
import mk.ukim.finki.wp.service.PostService;
import mk.ukim.finki.wp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikePostController {

    private final LikePostService likePostService;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public LikePostController(LikePostService likePostService, UserService userService, PostService postService) {
        this.likePostService = likePostService;
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Void> likePost(@RequestParam Long userId, @RequestParam Long postId) {
        User user = userService.findById(userId);
        Post post = postService.findById(postId);
        likePostService.likePost(user, post);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unlikePost(@RequestParam Long userId, @RequestParam Long postId) {
        User user = userService.findById(userId);
        Post post = postService.findById(postId);
        likePostService.unlikePost(user, post);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> countLikes(@RequestParam Long postId) {
        Post post = postService.findById(postId);
        int likeCount = likePostService.countLikes(post);
        return ResponseEntity.ok(likeCount);
    }

    @GetMapping("/liked-posts")
    public List<Long> findLikedPostIds(@RequestParam Long userId) {
        User user = userService.findById(userId);
        return likePostService.findLikedPostIds(user);
    }

    @GetMapping("/is-liked")
    public ResponseEntity<Boolean> isLiked(@RequestParam Long userId, @RequestParam Long postId) {
        User user = userService.findById(userId);
        Post post = postService.findById(postId);
        boolean isLiked = likePostService.isLiked(user, post);
        return ResponseEntity.ok(isLiked);
    }
}
