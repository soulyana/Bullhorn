package me.soulyana.bullhorn;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

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

    @Autowired
    CloudinaryConfig cloudc;

    @GetMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("aPost", new Post());
        model.addAttribute("postList", posts.findAll());
        return "feed";
    }

    @GetMapping("/addpost")
    public String addPost(Model model) {
        model.addAttribute("aPost", new Post());
        return "postform";
    }

    @PostMapping("/savepost")
    public String savePost(@Valid @ModelAttribute("aPost") Post post, BindingResult result, Authentication auth) {
        if(result.hasErrors()) {
            return "index";
        }
        AppUser appUser = users.findByUsername(auth.getName());
        post.setAppUser(appUser);
        posts.save(post);
        return "redirect:/";
    }

    @GetMapping("/adduser")
    public String newAppUser(Model model) {
        model.addAttribute("appuser", new AppUser());
        return "register";
    }

    @PostMapping("/adduser")
    public String processAppUser(@ModelAttribute AppUser appUser, @RequestParam("file")MultipartFile file) {
        if(file.isEmpty()) {
            return "redirect:/adduser";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
         appUser.setDisplayImg(uploadResult.get("url").toString());
         users.save(appUser);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/adduser";
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerUser(Model model, Authentication auth) {
        if(auth == null) {
            model.addAttribute("user", new AppUser());
            return "register";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("user") AppUser user, BindingResult result, Model model,
                                      Authentication auth) {
        if(auth == null) {
            model.addAttribute("user", user);
            if(result.hasErrors()) {
                return "register";
            } else {
                users.save(user);
            }
            return "redirect:/";
        } else {
            return "redirect:/";
        }


    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/landing")
    public String showLandingPage() {
        return "landing";
    }

    @RequestMapping("/profile")
    public String showProfilePage() {
        return "profile";
    }

    @RequestMapping("/feed")
    public String showFeed() {
        return "feed";
    }

}
