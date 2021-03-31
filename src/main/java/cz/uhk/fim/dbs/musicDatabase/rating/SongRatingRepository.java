package cz.uhk.fim.dbs.musicDatabase.rating;

import cz.uhk.fim.dbs.musicDatabase.album.Album;
import cz.uhk.fim.dbs.musicDatabase.song.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRatingRepository extends JpaRepository<SongRating, Long> {
}
