package edu.miu.cs489.hsumin.personalbudgettracker.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDateTime;

@Embeddable
@Data
public class AuditData {

    private String createdBy;

    private String updatedBy;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

}
