package edu.baylor.ecs.athleticstorm.repository;

import edu.baylor.ecs.athleticstorm.model.rating.PersonType;
import edu.baylor.ecs.athleticstorm.model.rating.Rating;
import edu.baylor.ecs.athleticstorm.model.rating.RatingKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/******************************************************************************
 *
 * RatingRepository.java
 *
 * author: Ian laird
 *
 * Created 2/25/20
 *
 * © 2020
 *
 ******************************************************************************/
public interface RatingRepository extends JpaRepository<Rating, RatingKey> {

    public Optional<Rating> findByKeyNameAndKeyYear(String name, Integer year);

    public List<Rating> findAllByKeyYearAndType(Integer year, PersonType personType);

    public List<Rating> findAllByKeyYear(Integer year);

    public List<Rating> findAllByKey_Name(String name);
}
