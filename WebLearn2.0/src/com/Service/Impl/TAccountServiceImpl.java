package com.Service.Impl;

import com.Dao.Impl.TAccountDaoImpl;
import com.Dao.TAccountDao;
import com.Service.TAccountService;
import com.Util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Classname TAccountServiceImpl
 * @Date 2020/3/16 14:18
 * @Created by Falling Stars
 * @Description 转账项目业务层接口实现
 */
public class TAccountServiceImpl implements TAccountService {
    /**
     * 处理业务逻辑
     * (1)	查询转出账号有没有
     * (2)	查询转入账号有没有
     * (3)	根据转出账号取出转出账号余额,看看钱够不够
     * (4)	更新转出账号余额(扣钱)
     * (5)	根据转入账号取出转入账号余额
     * (6)	更新转入账号余额(加钱)
     *
     * @param outAccount  转出账号
     * @param intoAccount 转入账号
     * @param BalanceStr  金额
     * @return
     */
    @Override
    public boolean tAccount(String outAccount, String intoAccount, String BalanceStr) {
        int Balance = Integer.valueOf(BalanceStr);

        TAccountDao TAccountDao = new TAccountDaoImpl();
        Connection conn = null;
        boolean result = false;
        try {
            conn = DBUtil.getConn();
            System.out.println("tAccount：" + conn);
            conn.setAutoCommit(false);

            // (1)查询转出账号有没有
            if (TAccountDao.checkAccount(outAccount, conn)) {
                System.out.println("转出账号存在");
                //(2)查询转入账号有没有
                if (TAccountDao.checkAccount(intoAccount, conn)) {
                    System.out.println("转入账号存在");
                    //(3)根据转出账号取出转出账号余额,看看钱够不够
                    int zcBalance = TAccountDao.getBalanceByAccount(outAccount, conn);
                    System.out.println("转出账号余额:" + zcBalance);
                    if (zcBalance >= Balance) {
                        //(4)更新转出账号余额(扣钱)
                        TAccountDao.updateBalanceByAccount(outAccount, zcBalance - Balance, conn);
                        System.out.println("更新转出账号余额(扣钱)");
                        //(5)根据转入账号取出转入账号余额
                        int zrBalance = TAccountDao.getBalanceByAccount(intoAccount, conn);
                        System.out.println("转入账号取出转入账号余额");
                        //(6)更新转入账号余额(加钱)
                        TAccountDao.updateBalanceByAccount(intoAccount, zrBalance + Balance, conn);
                        System.out.println("更新转入账号余额(加钱)");
                        result = true;
                    }
                }
            }
            conn.commit();
        } catch (Exception e) {
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
