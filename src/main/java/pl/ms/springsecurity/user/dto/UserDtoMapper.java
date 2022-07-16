package pl.ms.springsecurity.user.dto;

import pl.ms.springsecurity.user.User;
import pl.ms.springsecurity.userrole.UserRole;

import java.util.Set;
import java.util.stream.Collectors;

public class UserDtoMapper {

    public static UserCredentialsDto mapToUserCredentialsDto(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        Set<String> roles = user.getRoles()
                .stream()
                .map(role -> role.getRole().name())
                .collect(Collectors.toSet());
        return new UserCredentialsDto(username, password, roles);
    }

    public static UserEditDto mapToUserEditDto(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        Long id = user.getId();
        return new UserEditDto(username, password, id);
    }

    public static UserViewDto mapToUserViewDto(User user) {
        String username = user.getUsername();
        Long id = user.getId();
        Set<String> roles = user.getRoles()
                .stream()
                .map(role -> role.getRole().getDisplayValue())
                .collect(Collectors.toSet());
        return new UserViewDto(id, username, roles);
    }

    public static UserRolesDto mapToUserRolesDto(User user) {
        String username = user.getUsername();
        Long id = user.getId();
        Set<UserRole> roles = user.getRoles();
        return new UserRolesDto(id, username,roles);
    }
}