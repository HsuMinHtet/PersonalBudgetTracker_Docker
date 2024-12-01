package edu.miu.cs489.hsumin.personalbudgettracker.util;

import edu.miu.cs489.hsumin.personalbudgettracker.model.AuditData;

import java.time.LocalDateTime;

import static edu.miu.cs489.hsumin.personalbudgettracker.util.AuditDataCreate.auditData;

public class AuditDataUpdate {

    public static AuditData populateAuditData(String loggedInUser) {
        LocalDateTime now = LocalDateTime.now();
        //AuditData auditData = new AuditData();
        auditData.setUpdatedBy(loggedInUser);
        auditData.setUpdatedOn(now);

        return auditData;
    }
}