package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.model.RoleName;
import edu.baylor.ecs.athleticstorm.model.auth.RoleName;
import edu.baylor.ecs.athleticstorm.payload.UserIdentityAvailability;
import edu.baylor.ecs.athleticstorm.payload.UserProfile;
import edu.baylor.ecs.athleticstorm.payload.UserSummary;
import edu.baylor.ecs.athleticstorm.security.CurrentUser;
import edu.baylor.ecs.athleticstorm.security.UserPrincipal;
import edu.baylor.ecs.athleticstorm.exception.ResourceNotFoundException;
import edu.baylor.ecs.athleticstorm.model.auth.User;
import edu.baylor.ecs.athleticstorm.repository.UserRepository;
import org.assertj.core.internal.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        // Gets first authority from UserPrincipal's list of authorities to use when creating userSummary
        return new UserSummary(currentUser.getId(), currentUser.getUsername(),
                Enum.valueOf(RoleName.class, currentUser.getAuthorities().iterator().next().getAuthority()));
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return new UserProfile(user.getId(), user.getUsername(), user.getCreatedAt());
    }

}
