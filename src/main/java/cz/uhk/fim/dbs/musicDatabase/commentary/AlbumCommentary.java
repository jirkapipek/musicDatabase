package cz.uhk.fim.dbs.musicDatabase.commentary;

import cz.uhk.fim.dbs.musicDatabase.album.Album;
import cz.uhk.fim.dbs.musicDatabase.article.Article;
import cz.uhk.fim.dbs.musicDatabase.user.model.User;

import javax.persistence.*;

//Třída mapující tabulku album_commentary, která uchvovává komentáře k albám s konstuktory, gettery, settery a užitečnými metodami nad daty
@Entity
@Table(name = "album_commentary")
public class AlbumCommentary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String content;

    @OneToOne
    @JoinColumn(name = "album_id", nullable = true)
    private Album album;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User user;

    public AlbumCommentary() {
    }

    public AlbumCommentary(long id, String content, Album album, User user) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.album = album;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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