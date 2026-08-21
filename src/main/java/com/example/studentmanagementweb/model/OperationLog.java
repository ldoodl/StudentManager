package com.example.studentmanagementweb.model;

import lombok.Data;
import java.util.Date;

@Data
public class OperationLog {

    private Long id;
    private String operator;
    private String operationType;
    private String targetId;
    private Date operationTime;

}
