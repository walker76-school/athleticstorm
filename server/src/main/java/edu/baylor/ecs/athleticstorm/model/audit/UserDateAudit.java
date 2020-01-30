package edu.baylor.ecs.athleticstorm.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.MappedSuperclass;

/**
 * Created by rajeevkumarsingh on 19/08/17.
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
