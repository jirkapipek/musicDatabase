package cz.uhk.fim.dbs.musicDatabase.interpret;

import cz.uhk.fim.dbs.musicDatabase.db.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterpretJpaService implements BaseEntityService<Interpret> {

    @Autowired
    private InterpretRepository interpretRepository;

    @Override
    public List<Interpret> findAll() {
        return interpretRepository.findAll();
    }

    @Override
    public Interpret findById(long id) {
        return interpretRepository.findById(id).get();
    }

    @Override
    public Interpret create(Interpret interpret) {
        return interpretRepository.save(interpret);
    }

    @Override
    public Interpret update(Interpret interpret) {
        return interpretRepository.save(interpret);
    }

    @Override
    public void deleteById(long id) {
        interpretRepository.deleteById(id);
    }
}
