package cz.uhk.fim.dbs.musicDatabase.commentary;

import cz.uhk.fim.dbs.musicDatabase.album.Album;
import cz.uhk.fim.dbs.musicDatabase.song.Song;
import cz.uhk.fim.dbs.musicDatabase.user.model.User;

import javax.persistence.*;

//Třída mapující tabulku song_commentary, která uchovává komentáře ke skladbám s konstuktory, gettery, settery a užitečnými metodami nad daty

@Entity
@Table(name = "song_commentary")
public class SongCommentary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String content;

    @OneToOne
    @JoinColumn(name = "song_id", nullable = true)
    private Song song;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User user;

    public SongCommentary() {
    }

    public SongCommentary(long id, String content, Song song, User user) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.song = song;
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

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}