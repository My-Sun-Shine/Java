package com.Service;
/**
 * @Classname TAccountService
 * @Date 2020/3/16 14:18
 * @Created by Falling Stars
 * @Description 转账项目业务层接口
 */
public interface TAccountService {

    /**
     * 业务层的参数：
     * 一般来讲业务层方法中的参数都是由控制器提供的接收的参数
     *
     * @param outAccount  转出账号
     * @param intoAccount 转入账号
     * @param BalanceStr  金额
     * @return
     */
    boolean tAccount(String outAccount, String intoAccount, String BalanceStr);
}
