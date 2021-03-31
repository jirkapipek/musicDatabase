package cz.uhk.fim.dbs.musicDatabase.musicEvent;

import cz.uhk.fim.dbs.musicDatabase.db.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class MusicEventJpaService implements BaseEntityService<MusicEvent> {

    @Autowired
    private MusicEventRepository musicEventRepository;

    @Override
    public List<MusicEvent> findAll() {
        return musicEventRepository.findAll();
    }

    @Override
    public MusicEvent findById(long id) {
        return musicEventRepository.findById(id).get();
    }

    @Override
    public MusicEvent create(MusicEvent musicEvent) {
        return musicEventRepository.save(musicEvent);
    }

    @Override
    public MusicEvent update(MusicEvent musicEvent) {
        return musicEventRepository.save(musicEvent);
    }

    @Override
    public void deleteById(long id) {
        musicEventRepository.deleteById(id);
    }

    public List<MusicEvent> findFirst5ByOrderByStartDate() {
        return musicEventRepository.findFirst5ByOrderByStartDate();
    }

    public List<MusicEvent> findFirst5ByStartDateGreaterThanEqual(Date date) {
        return musicEventRepository.findFirst5ByStartDateGreaterThanEqualOrderByStartDate(date);
    }

    public List<MusicEvent> findAllByOrderByStartDate() {
        return musicEventRepository.findAllByOrderByStartDate();
    }
}