package com.intent.BookStore.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intent.BookStore.aws.DynamoClient;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class ControllerAspect {

    private final DynamoClient dynamoClient;


    @Before("execution(* com.intent.BookStore.controller.*.*(..))")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        String path = getPathFromAnnotation(joinPoint);
        Object[] args = joinPoint.getArgs();
        Map<String, String> argumentsMap = getArgumentsMap(args);
        String httpMethod = getHttpMethod(joinPoint);
        String body = getRequestBody(args);

        dynamoClient.logRequest(path, argumentsMap, body, httpMethod);
    }

    private String getPathFromAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        GetMapping getMappingAnnotation = method.getAnnotation(GetMapping.class);
        if (getMappingAnnotation != null && getMappingAnnotation.value().length > 0) {
            return getMappingAnnotation.value()[0];
        }

        PostMapping postMappingAnnotation = method.getAnnotation(PostMapping.class);
        if (postMappingAnnotation != null && postMappingAnnotation.value().length > 0) {
            return postMappingAnnotation.value()[0];
        }

        PutMapping putMappingAnnotation = method.getAnnotation(PutMapping.class);
        if (putMappingAnnotation != null && putMappingAnnotation.value().length > 0) {
            return putMappingAnnotation.value()[0];
        }

        DeleteMapping deleteMappingAnnotation = method.getAnnotation(DeleteMapping.class);
        if (deleteMappingAnnotation != null && deleteMappingAnnotation.value().length > 0) {
            return deleteMappingAnnotation.value()[0];
        }

        PatchMapping patchMappingAnnotation = method.getAnnotation(PatchMapping.class);
        if (patchMappingAnnotation != null && patchMappingAnnotation.value().length > 0) {
            return patchMappingAnnotation.value()[0];
        }

        return "";
    }

    private Map<String, String> getArgumentsMap(Object[] args) {
        Map<String, String> argumentsMap = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if ((args[i] instanceof String || args[i] instanceof Number || args[i] instanceof Boolean) && !args[i].toString().isEmpty()) {
                argumentsMap.put("arg" + i, args[i].toString());
            }
        }
        return argumentsMap;
    }


    private String getHttpMethod(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof GetMapping) {
                return "GET";
            } else if (annotation instanceof PostMapping) {
                return "POST";
            } else if (annotation instanceof PutMapping) {
                return "PUT";
            } else if (annotation instanceof DeleteMapping) {
                return "DELETE";
            } else if (annotation instanceof PatchMapping) {
                return "PATCH";
            }
        }
        return "UNKNOWN";
    }

    private String getRequestBody(Object[] args) {
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                if (arg != null && !(arg instanceof String) && !(arg instanceof Number) && !(arg instanceof Boolean)) {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        return objectMapper.writeValueAsString(arg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "null";
    }
}
