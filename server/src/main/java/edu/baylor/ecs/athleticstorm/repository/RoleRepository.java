package edu.baylor.ecs.athleticstorm.repository;

import edu.baylor.ecs.athleticstorm.model.auth.Role;
import edu.baylor.ecs.athleticstorm.model.auth.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
