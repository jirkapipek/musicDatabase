package cz.uhk.fim.dbs.musicDatabase.interpret;

import cz.uhk.fim.dbs.musicDatabase.album.Album;
import cz.uhk.fim.dbs.musicDatabase.genre.Genre;
import cz.uhk.fim.dbs.musicDatabase.song.Song;
import cz.uhk.fim.dbs.musicDatabase.user.model.Role;

import javax.persistence.*;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Set;

//Třída mapující tabulku interpret s konstuktory, gettery, settery a užitečnými metodami nad daty
@Entity
@Table(name = "interpret")
public class Interpret {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] photo;

    @ManyToMany(mappedBy = "interprets", cascade = CascadeType.ALL)
    private List<Album> albums;

    @ManyToMany
    @JoinTable(
            name = "genres_interprets",
            joinColumns = @JoinColumn(
                    name = "interpret_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "genre_id", referencedColumnName = "id"))
    List<Genre> genres;

    @ManyToMany(mappedBy = "interprets", cascade = CascadeType.ALL)
    private List<Song> songs;

    public Interpret() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public String getImageBase64() {
        String encodedImage = Base64.getEncoder().encodeToString(photo);
        return encodedImage;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
