package com.example.studentmanagementweb.aspect;

import com.example.studentmanagementweb.model.OperationLog;
import com.example.studentmanagementweb.dao.OperationLogMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Autowired
    private OperationLogMapper logMapper;

    @Pointcut("execution(public * com.example.studentmanagementweb.service.StudentService.*(..))")
    public void serviceMethods() {}

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logOperation (JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String operator = SecurityContextHolder.getContext().getAuthentication().getName();
        OperationLog log = new OperationLog();
        log.setOperator(operator);

        if (methodName.equals("addStudent")) {
            log.setOperationType("ADD");

            Object[] args = joinPoint.getArgs();
            if (args.length > 0 && args[0] instanceof com.example.studentmanagementweb.model.Student) {
                com.example.studentmanagementweb.model.Student s = (com.example.studentmanagementweb.model.Student) args[0];
                log.setTargetId(s.getId());
            }
        } else if (methodName.equals("updateStudent")) {
                log.setOperationType("UPDATE");
                Object[] args = joinPoint.getArgs();
                if (args.length > 0 && args[0] instanceof com.example.studentmanagementweb.model.Student) {
                    com.example.studentmanagementweb.model.Student s = (com.example.studentmanagementweb.model.Student) args[0];
                    log.setTargetId(s.getId());
                }
            } else if (methodName.equals("deleteStudent")) {
                log.setOperationType("DELETE");
                Object[] args = joinPoint.getArgs();
                if (args.length > 0 && args[0] instanceof String) {
                    log.setTargetId((String) args[0]);
                }
            } else {
            return ;
        }
        logMapper.insert(log);

    }
}
