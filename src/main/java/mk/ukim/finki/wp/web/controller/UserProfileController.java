package mk.ukim.finki.wp.web.controller;

import mk.ukim.finki.wp.model.Post;
import mk.ukim.finki.wp.model.User;
import mk.ukim.finki.wp.service.PostService;
import mk.ukim.finki.wp.service.FollowService;
import mk.ukim.finki.wp.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserProfileController {

    private final UserService userService;
    private final PostService postService;
    private final FollowService followService;

    public UserProfileController(UserService userService, PostService postService, FollowService followService) {
        this.userService = userService;
        this.postService = postService;
        this.followService = followService;
    }

    @GetMapping("/user-profile/{userId}")
    public String getUserProfile(@PathVariable Long userId, Model model, @AuthenticationPrincipal User loggedInUser) {
        User user = userService.findById(userId);
        if (user == null) {
            return "redirect:/explore"; // Redirect to explore if user is not found
        }
        List<Post> posts = postService.findByUser(user);
        int followerCount = followService.countFollowers(user);
        int followingCount = followService.countFollowing(user);
        boolean isFollowing = followService.isFollowing(loggedInUser, user);

        model.addAttribute("bodyContent", "user-profile");
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        model.addAttribute("followerCount", followerCount);
        model.addAttribute("followingCount", followingCount);
        model.addAttribute("isFollowing", isFollowing);
        model.addAttribute("isOwnProfile", user.equals(loggedInUser)); // Check if it's the logged-in user's profile
        return "master-template";
    }
}
