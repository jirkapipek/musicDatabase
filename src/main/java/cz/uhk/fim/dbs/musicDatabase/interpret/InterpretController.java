package cz.uhk.fim.dbs.musicDatabase.interpret;

import cz.uhk.fim.dbs.musicDatabase.album.Album;
import cz.uhk.fim.dbs.musicDatabase.article.Article;
import cz.uhk.fim.dbs.musicDatabase.genre.GenreJpaService;
import cz.uhk.fim.dbs.musicDatabase.musicEvent.MusicEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/interpret")
public class InterpretController {

    @Autowired
    InterpretJpaService interpretJpaService;

    @Autowired
    GenreJpaService genreJpaService;

    @GetMapping({"", "/"})
    public String listInterprets(Model model) {

        List<Interpret> list = interpretJpaService.findAll();
        model.addAttribute("interpretList", list);
        model.addAttribute("title", "Interpreti");

        return "interpret/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {

        model.addAttribute("interpret", new Interpret());
        model.addAttribute("genreList", genreJpaService.findAll());
        model.addAttribute("title", "Přidání interpreta");

        return "interpret/create";
    }

    @GetMapping("/detail/{id}")
    public String showDetailInterpret(@PathVariable(name = "id") Long id, Model model) {

        Interpret interpret = interpretJpaService.findById(id);
        model.addAttribute("title", "Interpret - " + interpret.getName());
        model.addAttribute("interpret", interpret);

        return "interpret/detail";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable(name = "id") Long id, Model model) {

        Interpret interpret = interpretJpaService.findById(id);
        model.addAttribute("title", "Upravit interpreta");
        model.addAttribute("genreList", genreJpaService.findAll());
        model.addAttribute("interpret", interpret);

        return "interpret/edit";
    }

    @PostMapping("/create")
    public String saveInterpret(@ModelAttribute Interpret interpret, Model model, @RequestParam(value = "file") MultipartFile image) {

        if (interpret.getGenres().isEmpty() || interpret.getDescription().isEmpty() || interpret.getName() == "" || image.isEmpty()) {
            model.addAttribute("errorContent", "Zadej všechny údaje");
            model.addAttribute("clasic", "alert alert-danger");
            showCreateForm(model);
            model.addAttribute("title", "Přidání interpreta");

            return "interpret/create";
        }

        try {
            if (!image.isEmpty()) interpret.setPhoto(image.getBytes());
            else interpret.setPhoto(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        interpretJpaService.create(interpret);

        return "redirect:/interpret?success";
    }

    @PostMapping("/edit")
    public String editInterpret(@ModelAttribute Interpret interpret, Model model, @RequestParam(value = "file") MultipartFile image) {

        if (interpret.getDescription().isEmpty() || interpret.getName() == "") {
            model.addAttribute("errorContent", "Zadej všechny údaje");
            model.addAttribute("clasic", "alert alert-danger");
            showEditForm(interpret.getId(), model);
            model.addAttribute("title", "Přidání interpreta");
            return "interpret/edit";
        }

        if (!image.isEmpty()) {
            try {
                interpret.setPhoto(image.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        interpretJpaService.create(interpret);

        return "redirect:/interpret";
    }

    @RequestMapping("/delete/{id}")
    public String deleteInterpret(@PathVariable(name = "id") Long id) {

        interpretJpaService.deleteById(id);

        return "redirect:/interpret";
    }
}
