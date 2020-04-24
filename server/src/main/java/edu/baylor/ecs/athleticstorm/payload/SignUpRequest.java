/*
 * Filename: SignUpRequest.java
 * Author: Andrew Walker
 * Date Last Modified: 3/26/2020
 */

package edu.baylor.ecs.athleticstorm.payload;

import edu.baylor.ecs.athleticstorm.model.auth.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

/**
 * A request to sign up
 *
 * @author Andrew Walker
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(min = 6, max = 20)
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
