package mk.ukim.finki.wp.web.controller;

import mk.ukim.finki.wp.model.Comment;
import mk.ukim.finki.wp.model.Post;
import mk.ukim.finki.wp.service.CommentService;
import mk.ukim.finki.wp.service.LikePostService;
import mk.ukim.finki.wp.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import mk.ukim.finki.wp.model.User;

import java.util.List;

@Controller
public class PostController {

    private final PostService postService;
    private final LikePostService likePostService;
    private final CommentService commentService;

    public PostController(PostService postService, LikePostService likePostService, CommentService commentService) {
        this.postService = postService;
        this.likePostService = likePostService;
        this.commentService = commentService;
    }

    @GetMapping("/create_post")
    public String createPostPage(Model model) {
        model.addAttribute("bodyContent", "create_post");
        return "master-template";
    }

    @PostMapping("/create_post")
    public String createPost(@AuthenticationPrincipal User user, @RequestParam String content, @RequestParam String image) {
        postService.createPost(user, content, image);
        return "redirect:/explore";
    }

    @GetMapping("/explore")
    public String explore(Model model) {
        model.addAttribute("posts", postService.findAll());
        model.addAttribute("bodyContent", "home");
        return "master-template";
    }

    @GetMapping("/post/{postId}")
    public String getPost(@PathVariable Long postId, Model model, @AuthenticationPrincipal User user) {
        Post post = postService.findById(postId);
        if (post == null) {
            return "redirect:/explore";
        }
        int likeCount = likePostService.countLikes(post);
        List<Comment> comments = commentService.findByPost(post);
        boolean isLiked = likePostService.isLiked(user, post);

        model.addAttribute("bodyContent", "post");
        model.addAttribute("post", post);
        model.addAttribute("likeCount", likeCount);
        model.addAttribute("isLiked", isLiked);
        model.addAttribute("comments", comments);
        model.addAttribute("user", user);
        return "master-template";
    }

    @GetMapping("/post/{postId}/edit")
    public String editPostPage(@PathVariable Long postId, Model model) {
        Post post = postService.findById(postId);
        if (post == null) {
            return "redirect:/explore";
        }
        model.addAttribute("bodyContent", "edit_post");
        model.addAttribute("post", post);
        return "master-template";
    }

    @PostMapping("/post/{postId}/edit")
    public String editPost(@PathVariable Long postId, @RequestParam String content, @RequestParam String image) {
        Post post = postService.findById(postId);
        if (post == null) {
            return "redirect:/explore";
        }
        postService.updatePost(post, content, image);
        return "redirect:/explore";
    }

    @PostMapping("/post/{postId}/delete")
    public String deletePost(@PathVariable Long postId) {
        Post post = postService.findById(postId);
        if (post == null) {
            return "redirect:/explore";
        }
        postService.deletePost(post);
        return "redirect:/explore";
    }
}
