package com.Dao;

import java.sql.Connection;

/**
 * @Classname TAccountDao
 * @Date 2020/3/15 22:48
 * @Created by Falling Stars
 * @Description 转账项目方法接口
 */
public interface TAccountDao {
    /**
     * 查询账号是否存在
     *
     * @param account
     * @return
     */
    boolean checkAccount(String account);

    /**
     * 根据账号取余额
     *
     * @param account
     * @return
     */
    int getBalanceByAccount(String account);

    /**
     * 根据账号更余额
     *
     * @param account
     * @param balance
     */
    void updateBalanceByAccount(String account, int balance);
}
