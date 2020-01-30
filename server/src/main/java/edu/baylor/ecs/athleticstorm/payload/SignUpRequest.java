package edu.baylor.ecs.athleticstorm.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

/**
 * Created by rajeevkumarsingh on 02/08/17.
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
}
