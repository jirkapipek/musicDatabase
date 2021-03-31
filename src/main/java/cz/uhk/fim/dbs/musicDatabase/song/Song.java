package cz.uhk.fim.dbs.musicDatabase.song;

import cz.uhk.fim.dbs.musicDatabase.album.Album;
import cz.uhk.fim.dbs.musicDatabase.commentary.AlbumCommentary;
import cz.uhk.fim.dbs.musicDatabase.commentary.SongCommentary;
import cz.uhk.fim.dbs.musicDatabase.genre.Genre;
import cz.uhk.fim.dbs.musicDatabase.interpret.Interpret;
import cz.uhk.fim.dbs.musicDatabase.rating.AlbumRating;
import cz.uhk.fim.dbs.musicDatabase.rating.SongRating;
import cz.uhk.fim.dbs.musicDatabase.user.model.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private Date time;


    @ManyToMany
    @JoinTable(
            name = "albums_songs",
            joinColumns = @JoinColumn(
                    name = "song_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "album_id", referencedColumnName = "id"))
    List<Album> albums;

    @ManyToMany
    @JoinTable(
            name = "interprets_songs",
            joinColumns = @JoinColumn(
                    name = "song_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "interpret_id", referencedColumnName = "id"))
    List<Interpret> interprets;

    @ManyToMany
    @JoinTable(
            name = "genres_songs",
            joinColumns = @JoinColumn(
                    name = "song_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "genre_id", referencedColumnName = "id"))
    List<Genre> genres;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
    private List<SongCommentary> comments;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
    private List<SongRating> ratings;

    public Song() {
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
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

    public List<SongCommentary> getComments() {
        return comments;
    }

    public void setComments(List<SongCommentary> comments) {
        this.comments = comments;
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
