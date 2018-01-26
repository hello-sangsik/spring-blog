package xyz.sangsik.blog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.sangsik.blog.model.entity.Comment;
import xyz.sangsik.blog.service.CommentService;
import xyz.sangsik.blog.model.ResponseObject.CommentResponse;
import xyz.sangsik.blog.web.security.CustomUserDetails;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/comment/{postId}")
    public List<CommentResponse> comments(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.getComments(postId)
                .stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
        return comments;
    }

    @PostMapping("/comment")
    public CommentResponse add(Comment comment, @AuthenticationPrincipal CustomUserDetails activeUser) {
        if (activeUser == null) {
            throw new RuntimeException();
            // todo : exception 처리
        }
        comment.setAuthor(activeUser.getEntity());
        return new CommentResponse(commentService.add(comment));
    }


}
