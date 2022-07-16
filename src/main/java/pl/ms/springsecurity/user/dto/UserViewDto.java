package pl.ms.springsecurity.user.dto;

import pl.ms.springsecurity.userrole.UserRole;

import java.util.Set;

public class UserViewDto {
    private Long id;
    private String username;
    private Set<String> roles;

    public UserViewDto(Long id, String username, Set<String> roles) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
