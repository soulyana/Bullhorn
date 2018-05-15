package me.soulyana.bullhorn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class MainController {

    @Autowired
    AppUserRepository users;

    @Autowired
    AppRoleRepository roles;

    @Autowired
    PostRepository posts;

    @Autowired
    CommentRepository comments;

    @GetMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("aPost", new Post());
        model.addAttribute("postList", posts.findAll());
        return "index";
    }

    @GetMapping("/addpost")
    public String addPost(Model model) {
        model.addAttribute("aPost", new Post());
        return "postform";
    }

    @PostMapping("/savepost")
    public String savePost(@Valid @ModelAttribute("aPost") Post post, BindingResult result) {
        if(result.hasErrors()) {
            return "index";
        }
        posts.save(post);
        return "redirect:/";
    }

    @RequestMapping("/editpost")
    public String editPost(HttpServletRequest request, Model model) {
        long id = new Long (request.getParameter("id"));
        model.addAttribute("aPost", posts.findById(id).get());
        return "postform";
    }


}
