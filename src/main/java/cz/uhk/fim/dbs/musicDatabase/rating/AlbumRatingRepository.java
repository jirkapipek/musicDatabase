package cz.uhk.fim.dbs.musicDatabase.rating;

import cz.uhk.fim.dbs.musicDatabase.album.Album;
import cz.uhk.fim.dbs.musicDatabase.interpret.Interpret;
import cz.uhk.fim.dbs.musicDatabase.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlbumRatingRepository extends JpaRepository<AlbumRating, Long> {
    AlbumRating findByAlbum(Album album);
}
