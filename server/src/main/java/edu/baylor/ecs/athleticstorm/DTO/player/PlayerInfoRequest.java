package edu.baylor.ecs.athleticstorm.DTO.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PlayerInfoRequest {
    private int teamId;
    private int playerId;
    private String first_name;
    private String last_name;
    private int year;
}
