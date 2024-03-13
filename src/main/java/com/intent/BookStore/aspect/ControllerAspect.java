package com.intent.BookStore.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intent.BookStore.aws.DynamoClient;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.DefaultParameterNameDiscoverer;
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
        Map<String, String> argumentsMap = getArgumentsMap(args, joinPoint);
        String httpMethod = getHttpMethod(joinPoint);
        String body = getRequestBody(args);

        dynamoClient.logRequest(path, argumentsMap, body, httpMethod);
    }

    private String getPathFromAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        String[] pathVariables = signature.getParameterNames();
        String[] pathValues = getPathValues(joinPoint.getArgs(), pathVariables, parameterAnnotations);
        GetMapping getMappingAnnotation = method.getAnnotation(GetMapping.class);
        if (getMappingAnnotation != null && getMappingAnnotation.value().length > 0) {
            String path = getMappingAnnotation.value()[0];
            path = replacePathVariables(path, pathVariables, pathValues);
            return path;
        }

        PostMapping postMappingAnnotation = method.getAnnotation(PostMapping.class);
        if (postMappingAnnotation != null && postMappingAnnotation.value().length > 0) {
            String path = postMappingAnnotation.value()[0];
            path = replacePathVariables(path, pathVariables, pathValues);
            return path;

        }

        PutMapping putMappingAnnotation = method.getAnnotation(PutMapping.class);
        if (putMappingAnnotation != null && putMappingAnnotation.value().length > 0) {
            String path = putMappingAnnotation.value()[0];
            path = replacePathVariables(path, pathVariables, pathValues);
            return path;
        }

        DeleteMapping deleteMappingAnnotation = method.getAnnotation(DeleteMapping.class);
        if (deleteMappingAnnotation != null && deleteMappingAnnotation.value().length > 0) {
            String path = deleteMappingAnnotation.value()[0];
            path = replacePathVariables(path, pathVariables, pathValues);
            return path;
        }

        PatchMapping patchMappingAnnotation = method.getAnnotation(PatchMapping.class);
        if (patchMappingAnnotation != null && patchMappingAnnotation.value().length > 0) {
            String path = patchMappingAnnotation.value()[0];
            path = replacePathVariables(path, pathVariables, pathValues);
            return path;
        }

        return "";
    }

    private String[] getPathValues(Object[] args, String[] pathVariables, Annotation[][] parameterAnnotations) {
        String[] pathValues = new String[pathVariables.length];
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof PathVariable) {
                    pathValues[i] = args[i].toString();
                }
            }
        }
        return pathValues;
    }

    private String replacePathVariables(String path, String[] pathVariables, String[] pathValues) {
        for (int i = 0; i < pathVariables.length; i++) {
            if (pathValues[i] != null) {
                path = path.replace("{" + pathVariables[i] + "}", pathValues[i]);
            }
        }
        return path;
    }

    private Map<String, String> getArgumentsMap(Object[] args, JoinPoint joinPoint) {
        Map<String, String> argumentsMap = new HashMap<>();
        Annotation[][] parameterAnnotations = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterAnnotations();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String[] parameterNames = getParameterNames(method);
        for (int i = 0; i < args.length; i++) {
            boolean isPathVariable = false;
            boolean isRequestParam = false;
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof PathVariable) {
                    isPathVariable = true;
                    break;
                } else if (annotation instanceof RequestParam) {
                    isRequestParam = true;
                    break;
                }
            }
            if (!isPathVariable) {
                if (args[i] != null) {
                    if (isRequestParam) {
                        String paramName = parameterNames[i];
                        argumentsMap.put(paramName, args[i].toString());
                    }
                }
            }
        }
        return argumentsMap;
    }

    private String[] getParameterNames(Method method) {
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        return discoverer.getParameterNames(method);
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
