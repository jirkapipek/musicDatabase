package cz.uhk.fim.dbs.musicDatabase.album;

import cz.uhk.fim.dbs.musicDatabase.db.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumJpaService implements BaseEntityService<Album> {

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    @Override
    public Album findById(long id) {
        return albumRepository.findById(id).get();
    }

    @Override
    public Album create(Album album) {
        return albumRepository.save(album);
    }

    @Override
    public Album update(Album album) {
        return albumRepository.save(album);
    }

    @Override
    public void deleteById(long id) {
        albumRepository.deleteById(id);
    }
}
