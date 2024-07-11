package mk.ukim.finki.wp.web.controller;

import mk.ukim.finki.wp.model.Comment;
import mk.ukim.finki.wp.model.Post;
import mk.ukim.finki.wp.model.User;
import mk.ukim.finki.wp.service.CommentService;
import mk.ukim.finki.wp.service.PostService;
import mk.ukim.finki.wp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping
    public Comment addComment(@RequestParam Long userId, @RequestParam Long postId, @RequestParam String commentText) {
        User author = userService.findById(userId);
        Post post = postService.findById(postId);
        return commentService.addComment(author, post, commentText);
    }

    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
        Post post = postService.findById(postId);
        return commentService.findByPost(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        Comment comment = commentService.findById(id);
        commentService.deleteComment(comment);
        return ResponseEntity.noContent().build();
    }
}
