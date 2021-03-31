package cz.uhk.fim.dbs.musicDatabase.album;

import cz.uhk.fim.dbs.musicDatabase.commentary.AlbumCommentary;
import cz.uhk.fim.dbs.musicDatabase.genre.Genre;
import cz.uhk.fim.dbs.musicDatabase.interpret.Interpret;
import cz.uhk.fim.dbs.musicDatabase.rating.AlbumRating;
import cz.uhk.fim.dbs.musicDatabase.song.Song;
import cz.uhk.fim.dbs.musicDatabase.user.model.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

//Třída mapující tabulku album s konstuktory, gettery, settery a užitečnými metodami nad daty
@Entity
@Table(name = "album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] cover;

    @ManyToMany
    @JoinTable(
            name = "interprets_albums",
            joinColumns = @JoinColumn(
                    name = "album_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "interpret_id", referencedColumnName = "id"))
    List<Interpret> interprets;

    @ManyToMany
    @JoinTable(
            name = "genres_albums",
            joinColumns = @JoinColumn(
                    name = "album_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "genre_id", referencedColumnName = "id"))
    List<Genre> genres;

    @ManyToMany(mappedBy = "albums", cascade = CascadeType.ALL)
    private List<Song> songs;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<AlbumCommentary> comments;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<AlbumRating> ratings;

    public Album() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Interpret> getInterprets() {
        return interprets;
    }

    public void setInterprets(List<Interpret> interprets) {
        this.interprets = interprets;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenresList() {
        return new ArrayList<Genre>(genres);
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<AlbumCommentary> getComments() {
        return comments;
    }

    public void setComments(List<AlbumCommentary> comments) {
        this.comments = comments;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getImageBase64() {
        String encodedImage = Base64.getEncoder().encodeToString(cover);
        return encodedImage;
    }

    public double getAverageRating() {
        if (ratings.size() != 0) {
            int sum = 0;
            for (int i = 0; i < ratings.size(); i++) {
                sum += ratings.get(i).getRating();
            }
            return sum / ratings.size();
        } else return 0;
    }

    public boolean isRatedFromUser(User user) {
        for (int i = 0; i < ratings.size(); i++) {
            if (ratings.get(i).getUser().equals(user)) return true;
        }
        return false;
    }
}