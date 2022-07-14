package pl.ms.springsecurity.user.dto;

import pl.ms.springsecurity.user.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserCredentialsDtoMapper {

    public static UserCredentialsDto map(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        Set<String> roles = user.getRoles()
                .stream()
                .map(role -> role.getRole().name())
                .collect(Collectors.toSet());
        return new UserCredentialsDto(username, password, roles);
    }
}