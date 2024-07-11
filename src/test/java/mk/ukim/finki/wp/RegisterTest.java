package mk.ukim.finki.wp;

import mk.ukim.finki.wp.model.User;
import mk.ukim.finki.wp.model.enumerations.Role;
import mk.ukim.finki.wp.model.exceptions.InvalidUsernameOrPasswordException;
import mk.ukim.finki.wp.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.wp.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.wp.repository.UserRepository;
import mk.ukim.finki.wp.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RegisterTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserServiceImpl userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        User user = new User("username", "password", "name", "surname", Role.ROLE_USER);

        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("password");

        this.userService = Mockito.spy(new UserServiceImpl(this.repository, this.passwordEncoder));
    }

    @Test
    public void testSuccessfulRegister() {
        User user = this.userService.register("username", "password", "password", "Name", "Surname", Role.ROLE_USER);
        Mockito.verify(this.userService).register("username", "password", "password", "Name", "Surname", Role.ROLE_USER);

        Assert.assertNotNull("User is null", user);
        Assert.assertEquals("Username does not match", "username", user.getUsername());
    }

    @Test
    public void testNullUsername() {
        String username = null;
        String password = "password";
        Assert.assertThrows("InvalidArgException expected", InvalidUsernameOrPasswordException.class, () -> {
            User user = this.userService.register(username, password, password, "Name", "Surname", Role.ROLE_USER);
        });

        Mockito.verify(this.userService).register(username, password, password, "Name", "Surname", Role.ROLE_USER);
    }

    @Test
    public void testEmptyUsername() {
        String username = "";
        String password = "password";
        Assert.assertThrows("InvalidArgException expected", InvalidUsernameOrPasswordException.class, () -> {
            User user = this.userService.register(username, password, password, "Name", "Surname", Role.ROLE_USER);
        });

        Mockito.verify(this.userService).register(username, password, password, "Name", "Surname", Role.ROLE_USER);
    }

    @Test
    public void testNullPassword() {
        String username = "username";
        String password = null;
        Assert.assertThrows("InvalidArgException expected", InvalidUsernameOrPasswordException.class, () -> {
            User user = this.userService.register(username, password, password, "Name", "Surname", Role.ROLE_USER);
        });

        Mockito.verify(this.userService).register(username, password, password, "Name", "Surname", Role.ROLE_USER);
    }

    @Test
    public void testEmptyPassword() {
        String username = "username";
        String password = "";
        Assert.assertThrows("InvalidArgException expected", InvalidUsernameOrPasswordException.class, () -> {
            User user = this.userService.register(username, password, password, "Name", "Surname", Role.ROLE_USER);
        });

        Mockito.verify(this.userService).register(username, password, password, "Name", "Surname", Role.ROLE_USER);
    }

    @Test
    public void testPasswordsDoNotMatch() {
        String password = "password";
        String passwordConfirm = "otherPassword";

        Assert.assertThrows("PasswordsDoNotMatchException expected", PasswordsDoNotMatchException.class, () -> {
            User user = this.userService.register("username", password, passwordConfirm, "Name", "Surname", Role.ROLE_USER);
        });

        Mockito.verify(this.userService).register("username", password, passwordConfirm, "Name", "Surname", Role.ROLE_USER);
    }

    @Test
    public void testUsernameAlreadyExists() {
        User existingUser = new User("username", "password", "name", "surname", Role.ROLE_USER);
        Mockito.when(this.repository.findByUsername("username")).thenReturn(Optional.of(existingUser));

        Assert.assertThrows("UsernameAlreadyExistsException expected", UsernameAlreadyExistsException.class, () -> {
            User user = this.userService.register("username", "password", "password", "Name", "Surname", Role.ROLE_USER);
        });

        Mockito.verify(this.userService).register("username", "password", "password", "Name", "Surname", Role.ROLE_USER);
    }

}
