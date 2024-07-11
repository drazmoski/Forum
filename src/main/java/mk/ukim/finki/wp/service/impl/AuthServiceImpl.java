package mk.ukim.finki.wp.service.impl;
import mk.ukim.finki.wp.model.User;
import mk.ukim.finki.wp.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.wp.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.wp.repository.UserRepository;
import mk.ukim.finki.wp.service.AuthService;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private boolean credentialsInvalid(String username, String password) {
        return username == null || password == null || username.isEmpty() || password.isEmpty();
    }

    @Override
    public User login(String username, String password) {
        if (credentialsInvalid(username, password)) {
            throw new InvalidArgumentsException();
        }

        return userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidUserCredentialsException::new);
    }

//    @Override
//    public User register(String username, String password, String repeatedPassword, String name, String surname) {
//
////        if (credentialsInvalid(username, password)) {
////            throw new InvalidArgumentsException();
////        }
////
////        if (!password.equals(repeatedPassword)) {
////            throw new PasswordsDoNotMatchException();
////        }
////
////        if (!this.userRepository.findByUsername(username).isEmpty() ||
////        this.userRepository.findByUsername(username).isPresent()){
////            throw new UsernameAlreadyExistsException(username);
////        }
////
////        User user = new User(username, password, name, surname);
////        return userRepository.save(user);
//    }
}
