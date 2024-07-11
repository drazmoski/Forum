package mk.ukim.finki.wp.service;

import mk.ukim.finki.wp.model.User;

import java.util.List;

public interface AuthService {
    User login(String username, String password);
//    User register(String username, String password, String repeatedPassword, String name, String surname);
}
