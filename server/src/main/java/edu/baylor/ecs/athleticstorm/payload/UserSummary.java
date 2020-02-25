package edu.baylor.ecs.athleticstorm.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import ../util/AppConstants.java;

@Data
@AllArgsConstructor
public class UserSummary {
    private Long id;
    private String username;
    private Role role;
}
