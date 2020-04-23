/*
 * Filename: LoginRequest.java
 * Author: Andrew Walker
 * Date Last Modified: 1/30/2020
 */

package edu.baylor.ecs.athleticstorm.payload;

import edu.baylor.ecs.athleticstorm.model.auth.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * A request to login
 *
 * @author Andrew Walker
 */
@Data
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private RoleName roleName;
}
