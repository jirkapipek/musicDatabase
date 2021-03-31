package cz.uhk.fim.dbs.musicDatabase.user.service;


import cz.uhk.fim.dbs.musicDatabase.user.model.User;
import cz.uhk.fim.dbs.musicDatabase.user.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);

    User findById(long id);

    List<User> findAll();

    User findByFirstName(String firstName);

    void deleteUser(long id);
}
