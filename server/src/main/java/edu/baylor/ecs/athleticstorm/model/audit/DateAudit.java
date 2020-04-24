/*
 * Filename: DateAudit.java
 * Author: Andrew Walker
 * Date Last Modified: 1/3/2020
 */

package edu.baylor.ecs.athleticstorm.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

/**
 * For managing the dates of events within an object
 *
 * @author Andrew Walker
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
@Getter
public abstract class DateAudit implements Serializable {

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
