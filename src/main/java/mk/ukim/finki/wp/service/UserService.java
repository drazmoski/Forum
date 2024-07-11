package mk.ukim.finki.wp.service;

import mk.ukim.finki.wp.model.Post;
import mk.ukim.finki.wp.model.User;
import mk.ukim.finki.wp.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findById(Long id);
    User register(String username, String password, String repeatPassword, String name, String surname, Role role);
    User update(User user);
    List<User> findAll();
    void deleteUser(String username);
}
