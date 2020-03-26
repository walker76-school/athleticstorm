package edu.baylor.ecs.athleticstorm.payload;

import edu.baylor.ecs.athleticstorm.model.auth.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private RoleName roleName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
