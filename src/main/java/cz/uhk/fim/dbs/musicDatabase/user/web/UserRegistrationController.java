package cz.uhk.fim.dbs.musicDatabase.user.web;

import javax.validation.Valid;

import cz.uhk.fim.dbs.musicDatabase.user.model.User;
import cz.uhk.fim.dbs.musicDatabase.user.service.UserService;
import cz.uhk.fim.dbs.musicDatabase.user.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("title", "Registrace");
        return "register/registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                      BindingResult result, Model model) {

        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "Účet s tímto emailem již existuje.");
            model.addAttribute("errorContent", "Účet s tímto emailem již existuje.");
            model.addAttribute("clasic", "alert alert-danger");


        }
        if (userDto.getFirstName() == "" || userDto.getEmail() == "" || userDto.getLastName() == "" || userDto.getPassword() == "" || userDto.getConfirmPassword() == "") {
            model.addAttribute("errorContent", "Vyplň všechny povinná pole");
            model.addAttribute("clasic", "alert alert-danger");
        }

        if (result.hasErrors()) {
            return "register/registration";
        }

        userService.save(userDto);
        return "redirect:/login?success";
    }

}
