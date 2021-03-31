package cz.uhk.fim.dbs.musicDatabase.user.service;

import cz.uhk.fim.dbs.musicDatabase.db.BaseEntityService;
import cz.uhk.fim.dbs.musicDatabase.user.model.Role;
import cz.uhk.fim.dbs.musicDatabase.user.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleJpaService implements BaseEntityService<Role> {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(long id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteById(long id) {
        roleRepository.deleteById(id);
    }
}
