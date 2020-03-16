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
     * @param account
     * @poram conn
     * @return
     */
    boolean checkAccount(String account, Connection conn);

    /**
     * 根据账号取余额
     * @param account
     * @poram conn
     * @return
     */
    int getBalanceByAccount(String account, Connection conn);

    /**
     * 根据账号更余额
     * @param account
     * @param balance
     * @poram conn
     */
    void updateBalanceByAccount(String account, int balance, Connection conn);
}
