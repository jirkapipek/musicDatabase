package cz.uhk.fim.dbs.musicDatabase.song;

import cz.uhk.fim.dbs.musicDatabase.album.Album;
import cz.uhk.fim.dbs.musicDatabase.album.AlbumJpaService;
import cz.uhk.fim.dbs.musicDatabase.commentary.AlbumCommentary;
import cz.uhk.fim.dbs.musicDatabase.commentary.SongCommentary;
import cz.uhk.fim.dbs.musicDatabase.commentary.SongCommentaryJpaService;
import cz.uhk.fim.dbs.musicDatabase.genre.GenreJpaService;
import cz.uhk.fim.dbs.musicDatabase.interpret.InterpretJpaService;
import cz.uhk.fim.dbs.musicDatabase.rating.AlbumRating;
import cz.uhk.fim.dbs.musicDatabase.rating.SongRating;
import cz.uhk.fim.dbs.musicDatabase.rating.SongRatingJpaService;
import cz.uhk.fim.dbs.musicDatabase.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/song")

public class SongController {

    @Autowired
    AlbumJpaService albumJpaService;

    @Autowired
    InterpretJpaService interpretJpaService;

    @Autowired
    GenreJpaService genreJpaService;

    @Autowired
    SongJpaService songJpaService;

    @Autowired
    UserService userService;

    @Autowired
    SongRatingJpaService songRatingJpaService;

    @Autowired
    SongCommentaryJpaService songCommentaryJpaService;

    @GetMapping({"", "/"})
    public String listSong(Model model) {

        List<Song> list = songJpaService.findAll();

        Collections.sort(list, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return (int) o2.getAverageRating() - (int) o1.getAverageRating();
            }
        });

        model.addAttribute("songList", list);
        model.addAttribute("title", "Skladby");

        return "song/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {

        model.addAttribute("song", new Song());
        model.addAttribute("interpretList", interpretJpaService.findAll());
        model.addAttribute("genreList", genreJpaService.findAll());
        model.addAttribute("albumList", albumJpaService.findAll());
        model.addAttribute("title", "Přidání skladby");

        return "song/create";
    }

    @PostMapping("/create")
    public String saveSong(@ModelAttribute Song song, Model model, @RequestParam(name = "minutes") int minutes, @RequestParam(name = "seconds") int seconds) {

        System.out.println(minutes + ":" + seconds);
        song.setTime(new Time(00, minutes, seconds));
        songJpaService.create(song);

        return "redirect:/song";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable(name = "id") Long id, Model model) {

        Song a = songJpaService.findById(id);
        model.addAttribute("title", "Upravit skladbu");
        model.addAttribute("genreList", genreJpaService.findAll());
        model.addAttribute("interpretList", interpretJpaService.findAll());
        model.addAttribute("albumList", albumJpaService.findAll());
        model.addAttribute("song", a);

        return "song/edit";
    }

    @PostMapping("/edit")
    public String editSong(@ModelAttribute Song song, Model model) {

        if (song.getName().isEmpty() || song.getInterprets().isEmpty() || song.getGenres().isEmpty()) {
            model.addAttribute("errorContent", "Zadej všechny údaje");
            model.addAttribute("clasic", "alert alert-danger");
            showEditForm(song.getId(), model);
            model.addAttribute("title", "Upravit skladbu");

            return "song/edit";
        }

        songJpaService.create(song);

        return "redirect:/song";
    }

    @GetMapping("/detail/{id}")
    public String showDetailSong(@PathVariable(name = "id") Long id, Model model) {

        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        Song a = songJpaService.findById(id);
        model.addAttribute("title", "Skladba - " + a.getName());
        model.addAttribute("song", a);
        model.addAttribute("rating_ok", a.isRatedFromUser(userService.findByEmail(aut.getName())));
        model.addAttribute("commentary", new SongCommentary());
        model.addAttribute("rating", new SongRating());
        model.addAttribute("comments", a.getComments());

        return "song/detail";
    }

    @RequestMapping("/delete/{id}")
    public String deleteSong(@PathVariable(name = "id") Long id) {

        songJpaService.deleteById(id);

        return "redirect:/song";
    }

    @RequestMapping("detail/{id}/commentary/delete/{id_commentary}")
    public String deleteCommentary(@PathVariable(name = "id") Long id, @PathVariable(name = "id_commentary") Long id_commentary) {

        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        SongCommentary commentary = songCommentaryJpaService.findById(id_commentary);

        if (commentary.getUser().getEmail().equals(aut.getName()) || aut.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            songCommentaryJpaService.deleteById(id_commentary);
        }

        return "redirect:/song/detail/" + id;
    }


    @PostMapping("/comment/{id}")
    public String addComment(@PathVariable(name = "id") Long id, @ModelAttribute SongCommentary songCommentary) {

        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        Song a = songJpaService.findById(id);
        songCommentary.setUser(userService.findByEmail(aut.getName()));
        songCommentary.setSong(a);
        songCommentaryJpaService.create(songCommentary);

        return "redirect:/song/detail/" + id;
    }

    @PostMapping("/rating/{id}")
    public String addRating(@PathVariable(name = "id") Long id, @ModelAttribute SongRating songRating) {

        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        Song a = songJpaService.findById(id);

        if (!a.isRatedFromUser(userService.findByEmail(aut.getName()))) {
            songRating.setUser(userService.findByEmail(aut.getName()));
            songRating.setAlbum(a);
            songRatingJpaService.create(songRating);
        }

        return "redirect:/song/detail/" + id;
    }
}