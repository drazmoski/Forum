package mk.ukim.finki.wp.service.impl;

import mk.ukim.finki.wp.model.Follow;
import mk.ukim.finki.wp.model.User;
import mk.ukim.finki.wp.repository.FollowRepository;
import mk.ukim.finki.wp.service.FollowService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    public FollowServiceImpl(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    @Override
    public void followUser(User follower, User following) {
        if (!isFollowing(follower, following)) {
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowing(following);
            followRepository.save(follow);
        }
    }

    @Override
    public void unfollowUser(User follower, User following) {
        Follow follow = followRepository.findByFollowerAndFollowing(follower, following);
        if (follow != null) {
            followRepository.delete(follow);
        }
    }

    @Override
    public boolean isFollowing(User follower, User following) {
        return followRepository.existsByFollowerAndFollowing(follower, following);
    }

    @Override
    public int countFollowers(User user) {
        return followRepository.countByFollowing(user);
    }

    @Override
    public int countFollowing(User user) {
        return followRepository.countByFollower(user);
    }

    @Override
    public List<User> getFollowing(User user) {
        return followRepository.findByFollower(user).stream()
                .map(Follow::getFollowing)
                .collect(Collectors.toList());
    }

}
