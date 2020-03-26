package edu.baylor.ecs.athleticstorm.startUp;

import edu.baylor.ecs.athleticstorm.DTO.TeamColorRequest;
import edu.baylor.ecs.athleticstorm.model.team.Color;
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

        // see if already done or colors already exist in the DB
        if(setupComplete || colorRepository.count() > 0){
            setupComplete = true;
            return;
        }

//        saveTeamColors();
        setupComplete = true;
    }

//    @Transactional
//    public void saveTeamColors(){
//        RestTemplate restTemplate = new RestTemplate();
//        TeamColorRequest[] result = restTemplate.getForObject(TEAM_URL, TeamColorRequest[].class);
//        for(TeamColorRequest color : result){
//            saveTeamColor(new Color(color.getSchool(), color.getColor(), color.getAlt_color(), color.getLogos()[0]));
//        }
//    }

    @Transactional
    public void saveTeamColor(Color color){
        colorRepository.save(color);
    }
}