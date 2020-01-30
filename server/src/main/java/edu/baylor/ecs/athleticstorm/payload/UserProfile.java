package edu.baylor.ecs.athleticstorm.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class UserProfile {
    private Long id;
    private String username;
    private Instant joinedAt;
}
