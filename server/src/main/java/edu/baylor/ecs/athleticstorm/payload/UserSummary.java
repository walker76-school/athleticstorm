package edu.baylor.ecs.athleticstorm.payload;

import edu.baylor.ecs.athleticstorm.model.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummary {
    private Long id;
    private String username;
    private RoleName roleName;
}
