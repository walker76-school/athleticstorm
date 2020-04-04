package edu.baylor.ecs.athleticstorm.payload;
import edu.baylor.ecs.athleticstorm.model.auth.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserSummary {
    private Long id;
    private String username;
    private Set<RoleName> roleName;
}
