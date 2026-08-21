package com.example.studentmanagementweb.dao;

import com.example.studentmanagementweb.model.OperationLog;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OperationLogMapper {

    @Insert("INSERT INTO operation_log (operator, operation_type, target_id)" +
            " VALUES (#{operator}, #{operation_type}, #{target_id})")
    int insert(OperationLog log);

}
