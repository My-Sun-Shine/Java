package com.Service.Impl;

import com.Dao.Impl.TAccountDaoImpl;
import com.Dao.TAccountDao;
import com.Service.TAccountService;

/**
 * @Classname TAccountServiceImpl
 * @Date 2020/3/16 14:18
 * @Created by Falling Stars
 * @Description 转账项目业务层接口实现
 */
public class TAccountServiceImpl implements TAccountService {
    @Override
    public boolean tAccount(String outAccount, String intoAccount, String BalanceStr) {
        int Balance = Integer.valueOf(BalanceStr);

        TAccountDao TAccountDao = new TAccountDaoImpl();

        /**
         * 处理业务逻辑
         *  (1)	查询转出账号有没有
         *  (2)	查询转入账号有没有
         *  (3)	根据转出账号取出转出账号余额,看看钱够不够
         *  (4)	更新转出账号余额(扣钱)
         *  (5)	根据转入账号取出转入账号余额
         *  (6)	更新转入账号余额(加钱)
         */
        // (1)查询转出账号有没有
        if (TAccountDao.checkAccount(outAccount)) {
            //(2)查询转入账号有没有
            if (TAccountDao.checkAccount(intoAccount)) {
                //(3)根据转出账号取出转出账号余额,看看钱够不够
                int zcBalance = TAccountDao.getBalanceByAccount(outAccount);
                if (zcBalance >= Balance) {
                    //(4)更新转出账号余额(扣钱)
                    TAccountDao.updateBalanceByAccount(outAccount, zcBalance - Balance);
                    //(5)根据转入账号取出转入账号余额
                    int zrBalance = TAccountDao.getBalanceByAccount(intoAccount);
                    //(6)更新转入账号余额(加钱)
                    TAccountDao.updateBalanceByAccount(intoAccount, zrBalance + Balance);
                    return true;
                }
            }
        }
        return false;
    }
}
