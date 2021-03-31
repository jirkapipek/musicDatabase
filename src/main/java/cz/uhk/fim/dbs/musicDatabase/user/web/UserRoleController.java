package cz.uhk.fim.dbs.musicDatabase.user.web;

import cz.uhk.fim.dbs.musicDatabase.user.model.Role;
import cz.uhk.fim.dbs.musicDatabase.user.model.User;
import cz.uhk.fim.dbs.musicDatabase.user.service.RoleJpaService;
import cz.uhk.fim.dbs.musicDatabase.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
@RequestMapping("/userEdit")
public class UserRoleController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleJpaService roleJpaService;

    @GetMapping({"", "/"})
    public String showEditForm(Model model) {

        model.addAttribute("userList", userService.findAll());
        model.addAttribute("roleList", roleJpaService.findAll());
        model.addAttribute("title", "Přidat roli");

        return "administration/user";
    }

    @PostMapping("/edit")
    public String showEditForm(@RequestParam(name = "user") long userId, @RequestParam(name = "role") long roleId, Model model) {

        User user = userService.findById(userId);
        Collection<Role> roles = user.getRoles();
        if (!roles.contains(roleJpaService.findById(roleId))) {
            roles.add(roleJpaService.findById(roleId));
            user.setRoles(roles);
            userService.update(user);

        } else {
            model.addAttribute("errorContent", "Uživatel má už tuto roli");
            model.addAttribute("clasic", "alert alert-danger");
            showEditForm(model);
            return "administration/user";
        }
        return "redirect:/userEdit?success";
    }
}
