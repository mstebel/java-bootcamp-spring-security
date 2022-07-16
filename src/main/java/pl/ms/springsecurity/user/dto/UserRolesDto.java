package pl.ms.springsecurity.user.dto;

import pl.ms.springsecurity.userrole.UserRole;

import java.util.Set;

public class UserRolesDto {
    private Long id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    private Set<UserRole> roles;

    public UserRolesDto(Long id, String username, Set<UserRole> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
