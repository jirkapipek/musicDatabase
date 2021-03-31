package cz.uhk.fim.dbs.musicDatabase.album;


import cz.uhk.fim.dbs.musicDatabase.commentary.AlbumCommentaryJpaService;
import cz.uhk.fim.dbs.musicDatabase.commentary.AlbumCommentary;
import cz.uhk.fim.dbs.musicDatabase.genre.GenreJpaService;
import cz.uhk.fim.dbs.musicDatabase.interpret.InterpretJpaService;
import cz.uhk.fim.dbs.musicDatabase.musicEvent.MusicEvent;
import cz.uhk.fim.dbs.musicDatabase.rating.AlbumRating;
import cz.uhk.fim.dbs.musicDatabase.rating.AlbumRatingJpaService;
import cz.uhk.fim.dbs.musicDatabase.song.Song;
import cz.uhk.fim.dbs.musicDatabase.song.SongJpaService;
import cz.uhk.fim.dbs.musicDatabase.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//Třída obstrarává url /album a další její podadresy
@Controller
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    AlbumJpaService albumJpaService;

    @Autowired
    InterpretJpaService interpretJpaService;

    @Autowired
    GenreJpaService genreJpaService;

    @Autowired
    UserService userService;

    @Autowired
    AlbumCommentaryJpaService albumCommentaryJpaService;

    @Autowired
    SongJpaService songJpaService;

    @Autowired
    AlbumRatingJpaService albumRatingJpaService;

    @GetMapping({"", "/"})
    public String listAlbums(Model model) {

        List<Album> list = albumJpaService.findAll();

        Collections.sort(list, new Comparator<Album>() {
            @Override
            public int compare(Album o1, Album o2) {
                return (int) o2.getAverageRating() - (int) o1.getAverageRating();
            }
        });

        model.addAttribute("albumList", list);
        model.addAttribute("title", "Alba");

        return "album/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {

        model.addAttribute("album", new Album());
        model.addAttribute("interpretList", interpretJpaService.findAll());
        model.addAttribute("genreList", genreJpaService.findAll());
        model.addAttribute("title", "Přidání článku");

        return "album/create";
    }

    @PostMapping("/create")
    public String saveAlbum(@ModelAttribute Album album, Model model, @RequestParam(value = "file") MultipartFile image) {

        if (album.getGenres().isEmpty() || album.getInterprets().isEmpty() || album.getName() == "" || image.isEmpty()) {
            model.addAttribute("errorContent", "Zadej všechny údaje");
            model.addAttribute("clasic", "alert alert-danger");
            showCreateForm(model);
            model.addAttribute("title", "Přidání článku");

            return "album/create";
        }
        try {
            album.setCover(image.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        albumJpaService.create(album);

        return "redirect:/album?success";
    }

    @RequestMapping("/delete/{id}")
    public String deleteAlbum(@PathVariable(name = "id") Long id) {

        albumJpaService.deleteById(id);

        return "redirect:/album";
    }

    @GetMapping("/detail/{id}")
    public String showDetailAlbum(@PathVariable(name = "id") Long id, Model model) {

        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        Album a = albumJpaService.findById(id);
        model.addAttribute("title", "Album - " + a.getName());
        model.addAttribute("album", a);
        model.addAttribute("rating_ok", a.isRatedFromUser(userService.findByEmail(aut.getName())));
        model.addAttribute("commentary", new AlbumCommentary());
        model.addAttribute("rating", new AlbumRating());
        model.addAttribute("comments", a.getComments());

        return "album/detail";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable(name = "id") Long id, Model model) {

        Album a = albumJpaService.findById(id);
        model.addAttribute("title", "Upravit album");
        model.addAttribute("genreList", genreJpaService.findAll());
        model.addAttribute("interpretList", interpretJpaService.findAll());
        model.addAttribute("album", a);

        return "album/edit";
    }

    @RequestMapping("detail/{id}/commentary/delete/{id_commentary}")
    public String deleteCommentary(@PathVariable(name = "id") Long id, @PathVariable(name = "id_commentary") Long id_commentary) {

        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        AlbumCommentary commentary = albumCommentaryJpaService.findById(id_commentary);
        if (commentary.getUser().getEmail().equals(aut.getName()) || aut.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            albumCommentaryJpaService.deleteById(id_commentary);
        }

        return "redirect:/album/detail/" + id;
    }


    @PostMapping("/comment/{id}")
    public String addCommentary(@PathVariable(name = "id") Long id, @ModelAttribute AlbumCommentary albumCommentary) {

        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        Album a = albumJpaService.findById(id);
        albumCommentary.setUser(userService.findByEmail(aut.getName()));
        albumCommentary.setAlbum(a);
        albumCommentaryJpaService.create(albumCommentary);

        return "redirect:/album/detail/" + id;
    }

    @PostMapping("/rating/{id}")
    public String addRating(@PathVariable(name = "id") Long id, @ModelAttribute AlbumRating albumRating) {

        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        Album a = albumJpaService.findById(id);
        if (!a.isRatedFromUser(userService.findByEmail(aut.getName()))) {
            albumRating.setUser(userService.findByEmail(aut.getName()));
            albumRating.setAlbum(a);
            albumRatingJpaService.create(albumRating);
        }

        return "redirect:/album/detail/" + id;
    }

    @PostMapping("/edit")
    public String editAlbum(@ModelAttribute Album album, Model model, @RequestParam(value = "file") MultipartFile image) {

        if (album.getName().isEmpty() || album.getInterprets().isEmpty() || album.getGenres().isEmpty()) {
            model.addAttribute("errorContent", "Zadej všechny údaje");
            model.addAttribute("clasic", "alert alert-danger");
            showEditForm(album.getId(), model);
            model.addAttribute("title", "Upravit album");

            return "album/edit";
        }
        if (!image.isEmpty()) {
            try {
                album.setCover(null);
                album.setCover(image.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        albumJpaService.create(album);

        return "redirect:/album";
    }
}
