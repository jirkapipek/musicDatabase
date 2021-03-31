package cz.uhk.fim.dbs.musicDatabase.rating;

import cz.uhk.fim.dbs.musicDatabase.album.Album;
import cz.uhk.fim.dbs.musicDatabase.user.model.User;

import javax.persistence.*;

@Entity
@Table(name = "album_rating")
public class AlbumRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private int rating;

    @OneToOne
    @JoinColumn(name = "album_id", nullable = true)
    private Album album;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User user;

    public AlbumRating() {
    }

    public AlbumRating(long id, int rating, Album album, User user) {
        this.id = id;
        this.rating = rating;
        this.album = album;
        this.user = user;
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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
