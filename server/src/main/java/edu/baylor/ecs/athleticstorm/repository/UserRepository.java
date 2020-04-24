/*
 * Filename: UserRepository.java
 * Author: Andrew Walker
 * Date Last Modified: 2/25/2020
 */

package edu.baylor.ecs.athleticstorm.repository;

import edu.baylor.ecs.athleticstorm.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for User data
 *
 * @author Andrew Walker
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find by username
     * @param username username
     * @return a User by username
     */
    Optional<User> findByUsername(String username);

    /**
     * Returns if a username is taken
     * @param username username
     * @return if a username is taken
     */
    Boolean existsByUsername(String username);
}
