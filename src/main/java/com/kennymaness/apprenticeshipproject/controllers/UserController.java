package com.kennymaness.apprenticeshipproject.controllers;

import com.kennymaness.apprenticeshipproject.data.BlogPostData;
import com.kennymaness.apprenticeshipproject.data.UserRepository;
import com.kennymaness.apprenticeshipproject.models.BlogPost;
import com.kennymaness.apprenticeshipproject.models.User;
import com.kennymaness.apprenticeshipproject.data.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationController authenticationController;

    @GetMapping
    public String displayAllUsers(Model model) {
        model.addAttribute("title", "All Users");
        model.addAttribute("users", userRepository.findAll());
        return "users/index";
    }

    @GetMapping("blog/{user_id}")
    public String displayUserBlog(Model model,
                                  @PathVariable int user_id,
                                  HttpServletRequest request){

        User thisUser = userRepository.findById(user_id);
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        model.addAttribute("user_id", thisUser.getId());
        model.addAttribute("username", thisUser.getUsername());
        model.addAttribute("blogposts", thisUser.getBlogposts());
        model.addAttribute("title", thisUser.getUsername());

        boolean isSameUser = user.getId() == thisUser.getId();
        model.addAttribute("isSameUser", isSameUser);

        return "users/blog";
    }

    @PostMapping("blog/{user_id}")
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

    @PostMapping("blog")
    public String goToUserBlog(@PathVariable int user_id,
                               HttpServletRequest request,
                               Model model) {
        User user = UserData.getById(user_id);

        return "redirect:";
    }

}
