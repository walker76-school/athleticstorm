package edu.baylor.ecs.athleticstorm.repository;

import edu.baylor.ecs.athleticstorm.model.team.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, String> {

    Color findByTeamName(String teamName);
}
