package cz.uhk.fim.dbs.musicDatabase.rating;

import cz.uhk.fim.dbs.musicDatabase.album.Album;
import cz.uhk.fim.dbs.musicDatabase.db.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongRatingJpaService implements BaseEntityService<SongRating> {

    @Autowired
    private SongRatingRepository ratingRepository;

    @Override
    public List<SongRating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public SongRating findById(long id) {
        return ratingRepository.findById(id).get();
    }

    @Override
    public SongRating create(SongRating albumRating) {
        return ratingRepository.save(albumRating);
    }

    @Override
    public SongRating update(SongRating albumRating) {
        return ratingRepository.save(albumRating);
    }

    @Override
    public void deleteById(long id) {
        ratingRepository.deleteById(id);
    }
}
