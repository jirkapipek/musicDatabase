package cz.uhk.fim.dbs.musicDatabase.main;

import cz.uhk.fim.dbs.musicDatabase.article.ArticleJpaService;
import cz.uhk.fim.dbs.musicDatabase.musicEvent.MusicEventJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Controller
public class MainController {

    @Autowired
    MusicEventJpaService musicEventJpaService;

    @Autowired
    ArticleJpaService articleJpaService;

    @GetMapping("/")
    public String root(Model model) {

        model.addAttribute("articleList", articleJpaService.findFirst5ByOrderByDateDesc());
        model.addAttribute("title", "Hlavní stránka");
        java.util.Date date = new java.util.Date();
        model.addAttribute("musicEventList", musicEventJpaService.findFirst5ByStartDateGreaterThanEqual(new Date(date.getTime())));

        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {

        model.addAttribute("title", "Přihlásit se");
        return "register/login";

    }

    @GetMapping("/administration")
    public String administration(Model model) {

        model.addAttribute("title", "Administrace");
        return "administration/index";

    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }
}
