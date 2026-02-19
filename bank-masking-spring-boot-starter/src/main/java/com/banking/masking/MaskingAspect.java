package com.banking.masking;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MaskingAspect {

    private final MaskingService maskingService;

    public MaskingAspect(MaskingService maskingService) {
        this.maskingService = maskingService;
    }

    // Intercepts all methods in any RestController
    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object maskLogs(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        
        // Replace method arguments with masked versions
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                args[i] = maskingService.mask(args[i]);
            }
        }

        return joinPoint.proceed(args);
    }
}