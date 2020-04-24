/*
 * Filename: UserDateAudit.java
 * Author: Andrew Walker
 * Date Last Modified: 1/3/2020
 */

package edu.baylor.ecs.athleticstorm.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.MappedSuperclass;

/**
 * For managing the dates of events within a user
 *
 * @author Andrew Walker
 */
@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy", "updatedBy"},
        allowGetters = true
)
@Getter
public abstract class UserDateAudit extends DateAudit {

    @CreatedBy
    private Long createdBy;

    @LastModifiedBy
    private Long updatedBy;

}
