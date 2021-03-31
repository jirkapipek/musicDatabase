package cz.uhk.fim.dbs.musicDatabase.commentary;

import cz.uhk.fim.dbs.musicDatabase.db.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongCommentaryJpaService implements BaseEntityService<SongCommentary> {

    @Autowired
    private SongCommentaryRepository songCommentaryRepository;

    @Override
    public List<SongCommentary> findAll() {
        return songCommentaryRepository.findAll();
    }

    @Override
    public SongCommentary findById(long id) {
        return songCommentaryRepository.findById(id).get();
    }

    @Override
    public SongCommentary create(SongCommentary comment) {
        return songCommentaryRepository.save(comment);
    }

    @Override
    public SongCommentary update(SongCommentary comment) {
        return songCommentaryRepository.save(comment);
    }

    @Override
    public void deleteById(long id) {
        songCommentaryRepository.deleteById(id);
    }
}
