package cz.uhk.fim.dbs.musicDatabase.rating;

import cz.uhk.fim.dbs.musicDatabase.album.Album;
import cz.uhk.fim.dbs.musicDatabase.db.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumRatingJpaService implements BaseEntityService<AlbumRating> {

    @Autowired
    private AlbumRatingRepository ratingRepository;

    @Override
    public List<AlbumRating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public AlbumRating findById(long id) {
        return ratingRepository.findById(id).get();
    }

    @Override
    public AlbumRating create(AlbumRating albumRating) {
        return ratingRepository.save(albumRating);
    }

    @Override
    public AlbumRating update(AlbumRating albumRating) {
        return ratingRepository.save(albumRating);
    }

    @Override
    public void deleteById(long id) {
        ratingRepository.deleteById(id);
    }

    public AlbumRating findByAlbum(Album album) {
        return ratingRepository.findByAlbum(album);
    }
}
