package cz.uhk.fim.dbs.musicDatabase.user.repository;

import cz.uhk.fim.dbs.musicDatabase.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
