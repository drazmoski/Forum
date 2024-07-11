package mk.ukim.finki.wp.service.impl;

import mk.ukim.finki.wp.model.LikePost;
import mk.ukim.finki.wp.model.Post;
import mk.ukim.finki.wp.model.User;
import mk.ukim.finki.wp.repository.LikePostRepository;
import mk.ukim.finki.wp.service.LikePostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikePostServiceImpl implements LikePostService {

    private final LikePostRepository likeRepository;

    public LikePostServiceImpl(LikePostRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public void likePost(User user, Post post) {
        if (!likeRepository.existsByUserAndPost(user, post)) {
            LikePost like = new LikePost();
            like.setUser(user);
            like.setPost(post);
            likeRepository.save(like);
        }
    }

    @Override
    public void unlikePost(User user, Post post) {
        LikePost like = likeRepository.findByUserAndPost(user, post);
        if (like != null) {
            likeRepository.delete(like);
        }
    }

    @Override
    public int countLikes(Post post) {
        return likeRepository.countByPost(post);
    }

    @Override
    public List<Long> findLikedPostIds(User user) {
        return likeRepository.findByUser(user)
                .stream()
                .map(like -> like.getPost().getId())
                .collect(Collectors.toList());
    }

    @Override
    public boolean isLiked(User user, Post post) {
        return likeRepository.existsByUserAndPost(user, post);
    }
}
