package cz.uhk.fim.dbs.musicDatabase.song;

import cz.uhk.fim.dbs.musicDatabase.db.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongJpaService implements BaseEntityService<Song> {

    @Autowired
    SongRepository songRepository;

    @Override
    public List<Song> findAll() {
        return songRepository.findAll();
    }

    @Override
    public Song findById(long id) {
        return songRepository.findById(id).get();
    }

    @Override
    public Song create(Song song) {
        return songRepository.save(song);
    }

    @Override
    public Song update(Song song) {
        return songRepository.save(song);
    }

    @Override
    public void deleteById(long id) {
        songRepository.deleteById(id);
    }
}
