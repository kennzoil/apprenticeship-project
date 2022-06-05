package com.kennymaness.apprenticeshipproject.controllers;

import com.kennymaness.apprenticeshipproject.data.BlogPostRepository;
import com.kennymaness.apprenticeshipproject.data.CommentRepository;
import com.kennymaness.apprenticeshipproject.data.UserRepository;
import com.kennymaness.apprenticeshipproject.models.BlogPost;
import com.kennymaness.apprenticeshipproject.models.Comment;
import com.kennymaness.apprenticeshipproject.models.User;
import com.kennymaness.apprenticeshipproject.data.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BlogPostRepository blogPostRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AuthenticationController authenticationController;

    @GetMapping("users")
    public String displayAllUsers(Model model) {
        model.addAttribute("title", "All Users");
        model.addAttribute("users", userRepository.findAll());
        return "users/index";
    }

    @GetMapping("users/blog/{user_id}")
    public String displayUserBlog(Model model,
                                  @PathVariable int user_id,
                                  HttpServletRequest request){

        User user = userRepository.findById(user_id);
        HttpSession session = request.getSession();
        User thisUser = authenticationController.getUserFromSession(session);

        model.addAttribute("user_id", user.getId());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("blogposts", user.getBlogposts());
        model.addAttribute("title", user.getUsername());

        boolean isSameUser = thisUser.getId() == user.getId();
        model.addAttribute("isSameUser", isSameUser);

        return "users/blog";
    }

    @PostMapping("users/blog/{user_id}")
    public String renderUserBlog(HttpServletRequest request,
                                  Model model,
                                  @PathVariable int user_id) {

        User thisUser = UserData.getById(user_id);

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        model.addAttribute("user_id", user.getId());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("blogposts", user.getBlogposts());
        model.addAttribute("title", user.getUsername());

        return "users/blog";
    }

    @PostMapping("users/blog")
    public String goToUserBlog(@PathVariable int user_id,
                               HttpServletRequest request,
                               Model model) {
        User user = UserData.getById(user_id);

        return "redirect:";
    }

    @GetMapping("users/post/{blogpost_id}")
    public String displayUserBlogpost(@PathVariable int blogpost_id,
                                      HttpServletRequest request,
                                      Model model) {
        BlogPost thisBlogPost = blogPostRepository.findById(blogpost_id);
        HttpSession session = request.getSession();
        User thisUser = authenticationController.getUserFromSession(session);
        boolean belongsToThisUser = thisUser.getBlogposts().contains(thisBlogPost);

        model.addAttribute("author", thisBlogPost.getUser());
        model.addAttribute("author_id", thisBlogPost.getUser().getId());
        model.addAttribute("author_username", thisBlogPost.getUser().getUsername());
        model.addAttribute("blogpost", thisBlogPost);
        model.addAttribute("title", thisBlogPost.getTitle());
        model.addAttribute("body", thisBlogPost.getBody());
        model.addAttribute("blogpost_id", thisBlogPost.getId());
        model.addAttribute("submission_date", thisBlogPost.getSubmission_date());
        model.addAttribute("belongsToThisUser", belongsToThisUser);
        model.addAttribute("comments", thisBlogPost.getComments());

        model.addAttribute("comment", new Comment());

        return "users/post";
    }

    @PostMapping("users/post/{blogpost_id}")
    public String processWriteCommentForm(@PathVariable int blogpost_id,
                                          HttpServletRequest request,
                                          Model model,
                                          Comment newComment) {
        BlogPost thisBlogPost = blogPostRepository.findById(blogpost_id);
        HttpSession session = request.getSession();
        User author = authenticationController.getUserFromSession(session);
        boolean belongsToThisUser = author.getBlogposts().contains(thisBlogPost);

        commentRepository.save(new Comment(
                LocalDateTime.now(),
                newComment.getBody(),
                thisBlogPost,
                author
        ));

        model.addAttribute("author", thisBlogPost.getUser());
        model.addAttribute("author_id", author.getId());
        model.addAttribute("author_username", thisBlogPost.getUser().getUsername());
        model.addAttribute("blogpost", thisBlogPost);
        model.addAttribute("title", thisBlogPost.getTitle());
        model.addAttribute("body", thisBlogPost.getBody());
        model.addAttribute("blogpost_id", thisBlogPost.getId());
        model.addAttribute("submission_date", thisBlogPost.getSubmission_date());
        model.addAttribute("belongsToThisUser", belongsToThisUser);
        model.addAttribute("comments", thisBlogPost.getComments());

        model.addAttribute("comment", new Comment());

        return "users/post";
    }

}
