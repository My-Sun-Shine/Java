package spring.aop.learn09.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Classname CheckArgumentAspect
 * @Date 2020/4/5 21:46
 * @Created by Falling Stars
 * @Description
 */
@Aspect
@Component("CheckArgumentAspect")
public class CheckArgumentAspect {

    @Around(value = "execution(* spring.aop.learn09.service.NumberServiceImpl.addNumber(..))")
    public Integer myAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object reslut = null;
        if (args != null && args.length == 3) {
            boolean flag = false;
            for (Object arg : args) {
                int num = (int) arg;
                if (num < 0) {
                    flag = true;
                }
            }
            if (flag) {
                reslut = -1;
            } else {
                reslut = joinPoint.proceed();
            }
        }
        if (reslut == null) {
            reslut = -1;
        }
        return (Integer) reslut;
    }

}
