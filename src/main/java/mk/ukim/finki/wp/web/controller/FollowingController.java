package mk.ukim.finki.wp.web.controller;

import mk.ukim.finki.wp.model.Post;
import mk.ukim.finki.wp.model.User;
import mk.ukim.finki.wp.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FollowingController {

    private final PostService postService;

    public FollowingController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/following")
    public String getFollowingPosts(Model model, @AuthenticationPrincipal User user) {
        List<Post> posts = postService.findPostsByUsersFollowing(user);
        model.addAttribute("bodyContent", "following");
        model.addAttribute("posts", posts);
        return "master-template";
    }
}
