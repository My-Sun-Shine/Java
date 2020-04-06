package spring.aop.learn09.service;

import org.springframework.stereotype.Component;

/**
 * @Classname NumberServiceImpl
 * @Date 2020/4/5 21:11
 * @Created by Falling Stars
 * @Description
 */
@Component("NumberService")
public class NumberServiceImpl implements NumberService {
    @Override
    public Integer addNumber(Integer n1, Integer n2, Integer n3) {
        return n1 + n2 +n3;
    }
}
