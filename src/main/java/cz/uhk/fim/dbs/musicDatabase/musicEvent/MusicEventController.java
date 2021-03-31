package cz.uhk.fim.dbs.musicDatabase.musicEvent;

import cz.uhk.fim.dbs.musicDatabase.article.Article;
import cz.uhk.fim.dbs.musicDatabase.db.BaseEntityService;
import cz.uhk.fim.dbs.musicDatabase.user.service.UserService;
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
@RequestMapping("/musicEvent")
public class MusicEventController {

    @Autowired
    MusicEventJpaService musicEventJpaService;
    @Autowired
    UserService userService;

    @GetMapping({"", "/"})
    public String listMusicEvents(Model model) {

        List<MusicEvent> list = musicEventJpaService.findAllByOrderByStartDate();
        model.addAttribute("musicEventList", list);
        model.addAttribute("title", "Hudební události");

        return "musicEvent/index";
    }

    @GetMapping("/create")
    public String createMusicEventForm(Model model) {

        model.addAttribute("musicEvent", new MusicEvent());
        model.addAttribute("title", "Přidání nové hudební události");

        return "musicEvent/create";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable(name = "id") Long id, Model model) {

        MusicEvent a = musicEventJpaService.findById(id);
        model.addAttribute("title", "Upravit událost");
        model.addAttribute("musicEvent", a);

        return "musicEvent/edit";
    }

    @GetMapping("/detail/{id}")
    public String showDetailMusicEvent(@PathVariable(name = "id") Long id, Model model) {

        MusicEvent event = musicEventJpaService.findById(id);
        model.addAttribute("title", "Událost - " + event.getTitle());
        model.addAttribute("musicEvent", event);

        return "musicEvent/detail";
    }

    @PostMapping("/create")
    public String saveMusicEvent(@ModelAttribute MusicEvent musicEvent, Model model, @RequestParam(value = "file") MultipartFile image) {

        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        musicEvent.setUser(userService.findByEmail(aut.getName()));

        if (musicEvent.getTitle() == "" || musicEvent.getContent().isEmpty() || musicEvent.getStartDate().equals(null) || musicEvent.getEndDate().equals(null) || image.isEmpty()) {
            model.addAttribute("errorContent", "Zadej všechny údaje");
            model.addAttribute("clasic", "alert alert-danger");
            createMusicEventForm(model);
            model.addAttribute("title", "Přidání interpreta");
            return "musicEvent/create";
        }

        try {
            musicEvent.setPoster(image.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        musicEventJpaService.create(musicEvent);

        return "redirect:/musicEvent?success";
    }

    @PostMapping("/edit")
    public String editMusicEvent(@ModelAttribute MusicEvent musicEvent, Model model, @RequestParam(value = "file") MultipartFile image) {

        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        musicEvent.setUser(userService.findByEmail(aut.getName()));
        if (musicEvent.getTitle().isEmpty() || musicEvent.getContent().isEmpty()) {
            model.addAttribute("errorContent", "Zadej všechny údaje");
            model.addAttribute("clasic", "alert alert-danger");
            showEditForm(musicEvent.getId(), model);
            model.addAttribute("title", "Přidání hudební události");
            return "musicEvent/edit";
        }

        if (!image.isEmpty()) {
            try {
                musicEvent.setPoster(image.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        musicEventJpaService.create(musicEvent);

        return "redirect:/musicEvent";
    }

    @RequestMapping("/delete/{id}")
    public String deleteArticle(@PathVariable(name = "id") Long id) {

        musicEventJpaService.deleteById(id);

        return "redirect:/musicEvent";
    }
}
