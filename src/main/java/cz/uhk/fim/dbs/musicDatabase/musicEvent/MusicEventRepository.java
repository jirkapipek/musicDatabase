package cz.uhk.fim.dbs.musicDatabase.musicEvent;

import cz.uhk.fim.dbs.musicDatabase.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface MusicEventRepository extends JpaRepository<MusicEvent, Long> {

    List<MusicEvent> findFirst5ByOrderByStartDate();

    List<MusicEvent> findAllByOrderByStartDate();

    List<MusicEvent> findFirst5ByStartDateGreaterThanEqualOrderByStartDate(Date today);
}
