package kz.iitu.project.hashproject.controller;

import kz.iitu.project.hashproject.entities.Users;
import kz.iitu.project.hashproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    private Users getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser = (User)authentication.getPrincipal();
            Users myUser = userService.getUserByEmail(secUser.getUsername());
            return myUser;
        }
        return null;
    }

    @RequestMapping(value = "/")
    public String index(){
        return "forward:/index";
    }

    @GetMapping(value = "/index")
    public String index(Model model){
        Users user = getUserData();
        if (user != null){
            model.addAttribute("currentUser", getUserData());
        }
        return "index";
    }

    @GetMapping(value = "/signup")
    public String signUp(){
        return "signup";
    }

    @PostMapping(value = "/register")
    public String register(@RequestParam(name = "name") String name,
                           @RequestParam(name = "surname") String surname,
                           @RequestParam(name = "email") String email,
                           @RequestParam(name = "password") String password,
                           @RequestParam(name = "re-password") String rePassword) {

        if (password.equals(rePassword)) {

            Users user = new Users();
            user.setName(name);
            user.setSurname(surname);
            user.setPassword(password);
            user.setEmail(email);

            if (userService.createUser(user) != null){
                return "redirect:/signup?success";
            }
        }
        return "redirect:/signup?error";
    }

    @GetMapping(value = "/login")
    public String login(Model model){
        Users user = getUserData();
        if (user != null){
            model.addAttribute("currentUser", getUserData());
        }
        return "login";
    }

    @GetMapping(value = "/about")
    public String about(Model model){
        Users user = getUserData();
        if (user != null){
            model.addAttribute("currentUser", getUserData());
        }
        return "about";
    }

    @GetMapping(value = "/contact")
    public String contact(Model model){
        Users user = getUserData();
        if (user != null){
            model.addAttribute("currentUser", getUserData());
        }
        return "contact";
    }

}
