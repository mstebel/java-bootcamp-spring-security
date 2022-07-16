package pl.ms.springsecurity.config;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.ms.springsecurity.user.UserService;
import pl.ms.springsecurity.user.dto.UserCredentialsDto;

@Service
class CustomInMemoryUserDetailsManager implements UserDetailsService {
    private final UserService userService;

    public CustomInMemoryUserDetailsManager(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findCredentialsByUsername(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with username %s not found", username)));
    }

    private UserDetails createUserDetails(UserCredentialsDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .roles(dto.getRoles().toArray(String[]::new))
                .build();
    }
}