package pl.ms.springsecurity.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.ms.springsecurity.user.dto.*;
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
                .map(UserDtoMapper::mapToUserCredentialsDto);
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

    public List<UserViewDto> findAllWithoutCurrentUser() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findAll()
                .stream()
                .filter(user -> !user.getUsername().equals(currentUser.getName()))
                .map(UserDtoMapper::mapToUserViewDto)
                .collect(Collectors.toList());
    }

    public Optional<UserEditDto> getCurrentUserEditDto() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByUsername(username).map(UserDtoMapper::mapToUserEditDto);
    }

    public void updateUser(UserEditDto dto) {
        Optional<User> userOptionalDb = userRepository.findById(dto.getId());
        User userInDb = userOptionalDb.orElseThrow();
        userInDb.setUsername(dto.getUsername());
        String passwordHash = passwordEncoder.encode(dto.getPassword());
        userInDb.setPassword(passwordHash);
        userRepository.save(userInDb);
    }

    public Optional <UserRolesDto> findUserRolesDtoById(Long id) {
      return userRepository.findById(id).map(UserDtoMapper::mapToUserRolesDto);
    }

    public void updateRoles(UserRolesDto dto) {
        Optional<User> userOptionalDb = userRepository.findById(dto.getId());
        User userInDb = userOptionalDb.orElseThrow();
        userInDb.setRoles(dto.getRoles());
        userRepository.save(userInDb);
    }
    public List<UserRole> findAllRoles() {
       return userRoleRepository.findAll();
    }
}