package cz.uhk.fim.dbs.musicDatabase.article;

import cz.uhk.fim.dbs.musicDatabase.commentary.AlbumCommentary;
import cz.uhk.fim.dbs.musicDatabase.db.BaseEntityService;
import cz.uhk.fim.dbs.musicDatabase.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//Třída obstrarává url /article a další její podadresy

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleJpaService articleJpaService;

    @Autowired
    UserService userService;

    @GetMapping({"", "/"})
    public String listArticles(Model model) {

        List<Article> list = articleJpaService.findAllByOrderByDateDesc();
        model.addAttribute("articleList", list);
        model.addAttribute("title", "Články");

        return "article/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {

        model.addAttribute("article", new Article());
        model.addAttribute("title", "Přidání článku");

        return "article/create";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable(name = "id") Long id, Model model) {

        Article a = articleJpaService.findById(id);
        model.addAttribute("title", "Upravit článek");
        model.addAttribute("article", a);

        return "article/edit";
    }

    @GetMapping("/detail/{id}")
    public String showDetailArticle(@PathVariable(name = "id") Long id, Model model) {

        Article a = articleJpaService.findById(id);
        model.addAttribute("title", "Článek - " + a.getTitle());
        model.addAttribute("article", a);

        return "article/detail";
    }

    @RequestMapping("/delete/{id}")
    public String deleteArticle(@PathVariable(name = "id") Long id) {

        articleJpaService.deleteById(id);

        return "redirect:/article";
    }

    @PostMapping("/create")
    public String saveArticle(@ModelAttribute Article article, Model model) {
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        if (article.getContent() == "" || article.getTitle() == "") {
            model.addAttribute("errorContent", "Vyplň všechny povinná pole");
            model.addAttribute("clasic", "alert alert-danger");
            return "article/create";
        }

        Article newArticle = article;
        newArticle.setUser(userService.findByEmail(aut.getName()));
            newArticle.setDate(new Date());

        articleJpaService.create(newArticle);

        return "redirect:/article?success";
    }

}
