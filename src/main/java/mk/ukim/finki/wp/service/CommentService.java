package mk.ukim.finki.wp.service;


import mk.ukim.finki.wp.model.Comment;
import mk.ukim.finki.wp.model.Post;
import mk.ukim.finki.wp.model.User;

import java.util.List;

public interface CommentService {
    Comment addComment(User author, Post post, String commentText);
    List<Comment> findByPost(Post post);
    void deleteComment(Comment comment);
    Comment findById(Long commentId);
}
