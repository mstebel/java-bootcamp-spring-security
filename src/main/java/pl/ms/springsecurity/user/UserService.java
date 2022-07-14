package pl.ms.springsecurity.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.ms.springsecurity.user.dto.UserCredentialsDto;
import pl.ms.springsecurity.user.dto.UserCredentialsDtoMapper;
import pl.ms.springsecurity.user.dto.UserRegistrationDto;
import pl.ms.springsecurity.userrole.Role;
import pl.ms.springsecurity.userrole.UserRole;
import pl.ms.springsecurity.userrole.UserRoleRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserCredentialsDto> findCredentialsByUsername(String userName) {
        return userRepository.findByUsername(userName)
                .map(UserCredentialsDtoMapper::map);
    }

    @Transactional
    public void register(UserRegistrationDto registration) {
        User user = new User();
        user.setUsername(registration.getUsername());
        String passwordHash = passwordEncoder.encode(registration.getPassword());
        user.setPassword(passwordHash);
        assignUserRole(user);
        userRepository.save(user);
    }

    private void assignUserRole(User user) {
        Optional<UserRole> userRole = userRoleRepository.findByRole(Role.USER);
        userRole.ifPresentOrElse(
                role -> user.getRoles().add(role),
                () -> {
                    throw new NoSuchElementException();
                }
        );
    }

    public List<User> findAllWithoutCurrentUser() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findAll()
                .stream()
                .filter(user -> !user.getUsername().equals(currentUser.getName()))
                .collect(Collectors.toList());
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByUsername(username).orElseThrow();
    }

    public void updateUser(User user, Long id) {
        Optional<User> userOptionalDb = userRepository.findById(id);
        User userInDb = userOptionalDb.orElseThrow();
        userInDb.setUsername(user.getUsername());
        String passwordHash = passwordEncoder.encode(user.getPassword());
        userInDb.setPassword(passwordHash);
        userRepository.save(userInDb);
    }

    public User findUserById(Long id) {
       return userRepository.findById(id).orElseThrow();
    }

    public void updateRoles(User user, Long id) {
        User userInDb = userRepository.findById(id).orElseThrow();
        userInDb.setRoles(user.getRoles());
        userRepository.save(userInDb);
    }
    public List<UserRole> findAllRoles() {
       return userRoleRepository.findAll();
    }
}