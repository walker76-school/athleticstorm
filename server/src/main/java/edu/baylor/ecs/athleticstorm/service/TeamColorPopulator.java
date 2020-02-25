package edu.baylor.ecs.athleticstorm.service;

import edu.baylor.ecs.athleticstorm.DTO.APITeamColor;
import edu.baylor.ecs.athleticstorm.model.Color;
import edu.baylor.ecs.athleticstorm.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * populates initial data in the DB
 */
@Component
public class TeamColorPopulator implements ApplicationListener<ContextRefreshedEvent> {

    private static final String TEAM_URL = "https://api.collegefootballdata.com/teams";

    // shows if the DB setup is done
    private static boolean setupComplete = false;

    @Autowired
    private ColorRepository colorRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event){
        if(setupComplete){
            return;
        }
        saveTeamColors();
        setupComplete = true;
    }

    @Transactional
    public void saveTeamColors(){
        RestTemplate restTemplate = new RestTemplate();
        APITeamColor [] result = restTemplate.getForObject(TEAM_URL, APITeamColor[].class);
        for(APITeamColor color : result){
            saveTeamColor(new Color(color.getSchool(), color.getColor()));
        }
    }

    @Transactional
    public void saveTeamColor(Color color){
        colorRepository.save(color);
    }
}