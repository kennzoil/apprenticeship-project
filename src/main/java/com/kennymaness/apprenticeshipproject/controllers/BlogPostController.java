package com.kennymaness.apprenticeshipproject.controllers;

import com.kennymaness.apprenticeshipproject.data.BlogPostRepository;
import com.kennymaness.apprenticeshipproject.data.UserRepository;
import com.kennymaness.apprenticeshipproject.models.BlogPost;
import com.kennymaness.apprenticeshipproject.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("blogposts")
public class BlogPostController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private AuthenticationController authenticationController;

    @GetMapping("/")
    public String renderBlogpostForm() {

        return "blogposts";
    }

    @GetMapping("create")
    public String renderCreateBlogpostForm(Model model) {

        model.addAttribute("title", "Create a Post");
        model.addAttribute("blogpost", new BlogPost());
        return "blogposts/create";
    }

    @PostMapping("create")
    public String processCreateBlogpostForm(HttpServletRequest request,
                                            Model model,
                                            BlogPost newBlogpost){
        HttpSession session = request.getSession();
        User author = authenticationController.getUserFromSession(session);

        blogPostRepository.save(new BlogPost(
                LocalDateTime.now(),
                newBlogpost.getTitle(),
                newBlogpost.getBody(),
                author));

        return "blogposts/index";
    }

    @GetMapping("edit/{blogpostId}")
    public String renderEditBlogpostForm(Model model,
                                         @PathVariable int blogpostId) {

        BlogPost blogPostToBeEdited = blogPostRepository.findById(blogpostId);

        model.addAttribute("title", blogPostToBeEdited.getTitle());
        model.addAttribute("body", blogPostToBeEdited.getBody());
        model.addAttribute("blogpostId", blogPostToBeEdited.getId());

        return "blogposts/edit";
    }

    @PostMapping("edit")
    public String processEditBlogpostForm(int blogpostId, String title, String body) {

        BlogPost blogPost = blogPostRepository.findById(blogpostId);
        blogPost.setTitle(title);
        blogPost.setBody(body);
        blogPostRepository.save(blogPost);
        return "blogposts/index";
    }

    // TODO: Implement soft deletion! https://www.baeldung.com/delete-with-hibernate
    @GetMapping("delete/{blogpostId}")
    public String processDeleteBlogpostForm(HttpServletRequest request,
                                            Model model,
                                            @PathVariable int blogpostId) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        BlogPost blogPost = blogPostRepository.findById(blogpostId);

        model.addAttribute("title", blogPost.getTitle());
        model.addAttribute("body", blogPost.getBody());
        model.addAttribute("blogpostId", blogpostId);

        return "blogposts/delete";
    }

    @PostMapping("delete")
    public String processDeleteBlogpostForm(int blogpostId) {
        BlogPost blogPost = blogPostRepository.findById(blogpostId);
        blogPostRepository.delete(blogPost);
        return "blogposts/index";
    }

}
