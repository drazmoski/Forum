package mk.ukim.finki.wp.web.controller;

import mk.ukim.finki.wp.model.User;
import mk.ukim.finki.wp.service.FollowService;
import mk.ukim.finki.wp.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;
    private final UserService userService;

    public FollowController(FollowService followService, UserService userService) {
        this.followService = followService;
        this.userService = userService;
    }

    @PostMapping("/{userId}")
    public String followUser(@PathVariable Long userId, @AuthenticationPrincipal User follower) {
        User following = userService.findById(userId);
        if (following != null && !follower.equals(following)) {
            followService.followUser(follower, following);
        }
        return "redirect:/user-profile/" + userId;
    }

    @PostMapping("/unfollow/{userId}")
    public String unfollowUser(@PathVariable Long userId, @AuthenticationPrincipal User follower) {
        User following = userService.findById(userId);
        if (following != null && !follower.equals(following)) {
            followService.unfollowUser(follower, following);
        }
        return "redirect:/user-profile/" + userId;
    }
}
