package kfs.vueNotes.services;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class AspectLogger {

    @PostConstruct
    public void init() {
        log.info("AL init");
    }

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void springSvcPointcut() {
    }

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void springCrtlPointcut() {
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void springRestCrtlPointcut() {
    }

    @Around("springSvcPointcut() || springCrtlPointcut() || springRestCrtlPointcut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Start {}, {}", joinPoint.getSignature(), Arrays.deepToString(joinPoint.getArgs()));
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        Duration d = Duration.of(System.currentTimeMillis() - start, ChronoUnit.MILLIS);
        log.info("Executed {} in {}", joinPoint.getSignature(), d);
        return proceed;
    }

}
