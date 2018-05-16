package me.soulyana.bullhorn;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/adduser")
    public String newAppUser(Model model) {
        model.addAttribute("appuser", new AppUser());
        return "registration";
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


}
