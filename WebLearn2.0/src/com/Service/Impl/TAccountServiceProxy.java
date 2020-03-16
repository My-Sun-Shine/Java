package com.Service.Impl;

import com.Service.TAccountService;
import com.Util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Classname TaccountServiceProxy
 * @Date 2020/3/16 21:50
 * @Created by Falling Stars
 * @Description 加入代理模式
 */
public class TAccountServiceProxy implements TAccountService {
    private TAccountServiceImpl tAccountService;

    public TAccountServiceProxy(TAccountServiceImpl tAccountService) {
        this.tAccountService = tAccountService;
    }

    @Override
    public boolean tAccount(String outAccount, String intoAccount, String BalanceStr) {
        Connection conn = null;
        boolean result = false;
        try {
            conn = DBUtil.getConn();
            System.out.println("tAccount：" + conn);
            conn.setAutoCommit(false);
            result = tAccountService.tAccount(outAccount, intoAccount, BalanceStr);
            conn.commit();

        } catch (SQLException e) {
            System.out.println(e.toString());
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println(e1.toString());
            }
        } finally {
            DBUtil.close(conn, null, null);
        }
        return result;
    }
}
