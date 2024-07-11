package mk.ukim.finki.wp.service.impl;

import mk.ukim.finki.wp.model.Comment;
import mk.ukim.finki.wp.model.Post;
import mk.ukim.finki.wp.model.User;
import mk.ukim.finki.wp.model.exceptions.InvalidUsernameOrPasswordException;
import mk.ukim.finki.wp.repository.CommentRepository;
import mk.ukim.finki.wp.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment addComment(User author, Post post, String commentText) {
        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setPost(post);
        comment.setComment(commentText);
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(InvalidUsernameOrPasswordException::new);
    }
}
