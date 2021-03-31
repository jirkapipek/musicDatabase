package cz.uhk.fim.dbs.musicDatabase.rating;

import cz.uhk.fim.dbs.musicDatabase.album.Album;
import cz.uhk.fim.dbs.musicDatabase.song.Song;
import cz.uhk.fim.dbs.musicDatabase.user.model.User;

import javax.persistence.*;

@Entity
@Table(name = "song_rating")
public class SongRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private int rating;

    @OneToOne
    @JoinColumn(name = "song_id", nullable = true)
    private Song song;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User user;

    public SongRating() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Song getAlbum() {
        return song;
    }

    public void setAlbum(Song song) {
        this.song = song;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
