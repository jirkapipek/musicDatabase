package cz.uhk.fim.dbs.musicDatabase.user.repository;

import cz.uhk.fim.dbs.musicDatabase.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByFirstName(String firstName);


}
