package cz.uhk.fim.dbs.musicDatabase.commentary;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SongCommentaryRepository extends JpaRepository<SongCommentary, Long> {
}
