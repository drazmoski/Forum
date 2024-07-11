package mk.ukim.finki.wp.service;

import mk.ukim.finki.wp.model.User;
import mk.ukim.finki.wp.model.Follow;

import java.util.List;

public interface FollowService {
    void followUser(User follower, User following);
    void unfollowUser(User follower, User following);
    boolean isFollowing(User follower, User following);
    int countFollowers(User user);
    int countFollowing(User user);
    public List<User> getFollowing(User user);
}
