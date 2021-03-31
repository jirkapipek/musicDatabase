package cz.uhk.fim.dbs.musicDatabase.genre;

import cz.uhk.fim.dbs.musicDatabase.album.Album;
import cz.uhk.fim.dbs.musicDatabase.interpret.Interpret;
import cz.uhk.fim.dbs.musicDatabase.song.Song;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

//Třída mapující tabulku genre s jednotlivými žánry s konstuktory, gettery, settery a užitečnými metodami nad daty

@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String genre;

    @ManyToMany(mappedBy = "genres")
    private List<Album> albums;

    @ManyToMany(mappedBy = "genres")
    private List<Interpret> interprets;

    @ManyToMany(mappedBy = "genres")
    private List<Song> songs;


    public Genre() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}