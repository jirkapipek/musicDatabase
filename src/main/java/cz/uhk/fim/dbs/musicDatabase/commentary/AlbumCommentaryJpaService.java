package cz.uhk.fim.dbs.musicDatabase.commentary;

import cz.uhk.fim.dbs.musicDatabase.db.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumCommentaryJpaService implements BaseEntityService<AlbumCommentary> {

    @Autowired
    private AlbumCommentaryRepository albumCommentaryRepository;

    @Override
    public List<AlbumCommentary> findAll() {
        return albumCommentaryRepository.findAll();
    }

    @Override
    public AlbumCommentary findById(long id) {
        return albumCommentaryRepository.findById(id).get();
    }

    @Override
    public AlbumCommentary create(AlbumCommentary comment) {
        return albumCommentaryRepository.save(comment);
    }

    @Override
    public AlbumCommentary update(AlbumCommentary comment) {
        return albumCommentaryRepository.save(comment);
    }

    @Override
    public void deleteById(long id) {
        albumCommentaryRepository.deleteById(id);
    }
}
