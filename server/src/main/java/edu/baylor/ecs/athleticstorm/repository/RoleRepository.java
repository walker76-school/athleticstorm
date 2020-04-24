/*
 * Filename: RoleRepository.java
 * Author: Andrew Walker
 * Date Last Modified: 2/25/2020
 */

package edu.baylor.ecs.athleticstorm.repository;

import edu.baylor.ecs.athleticstorm.model.auth.Role;
import edu.baylor.ecs.athleticstorm.model.auth.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Role data
 *
 * @author Andrew Walker
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find role by name
     * @param roleName name of a role
     * @return a Role by name
     */
    Optional<Role> findByName(RoleName roleName);
}
