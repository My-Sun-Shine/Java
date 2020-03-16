package com.Dao.Impl;

import com.Dao.TAccountDao;
import com.Util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Classname TAccountDaoImpl
 * @Date 2020/3/15 22:50
 * @Created by Falling Stars
 * @Description 转账项目方法接口实现
 */
public class TAccountDaoImpl implements TAccountDao {

    /**
     * 查询账号是否存在
     *
     * @param account
     * @return
     */
    @Override
    public boolean checkAccount(String account) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select count(*) from t_account where account=?";
        boolean flag = true;
        try {
            conn = DBUtil.getConn();
            System.out.println("checkAccount：" + conn);
            ps = conn.prepareStatement(sql);
            ps.setString(1, account);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count != 1) {
                    //说明没查到
                    flag = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return flag;
    }

    /**
     * 根据账号取余额
     *
     * @param account
     * @return
     */
    @Override
    public int getBalanceByAccount(String account) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select balance from t_account where account=?";
        int balance = 0;

        try {
            conn = DBUtil.getConn();
            System.out.println("getBalanceByAccount：" + conn);
            ps = conn.prepareStatement(sql);
            ps.setString(1, account);
            rs = ps.executeQuery();
            if (rs.next()) {
                balance = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return balance;
    }

    /**
     * 根据账号更余额
     *
     * @param account
     * @param balance
     */
    @Override
    public void updateBalanceByAccount(String account, int balance) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update t_account set balance=? where account=?";
        try {
            conn = DBUtil.getConn();
            System.out.println("updateBalanceByAccount：" + conn);
            ps = conn.prepareStatement(sql);
            ps.setInt(1, balance);
            ps.setString(2, account);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, null);
        }
    }
}
