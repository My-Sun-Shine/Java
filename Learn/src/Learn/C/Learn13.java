package Learn.C;

/**
 * @Classname Learn13
 * @Date 2020/2/29 13:59
 * @Created by Falling Stars
 * @Description 模拟取款
 */
public class Learn13 {
    public static void main(String[] args) {
        //先开户
        BankAccount abc = new BankAccount();

        //创建三个线程对象模拟三个人
        PersonThread p1 = new PersonThread(abc);
        PersonThread p2 = new PersonThread(abc);
        PersonThread p3 = new PersonThread(abc);

        p1.setName("线程T1");
        p2.setName("线程T2");
        p3.setName("线程T3");

        p1.start();
        p2.start();
        p3.start();
    }


}

/**
 * 银行类
 */
class BankAccount {
    private int balance = 10000;            //帐户余额

    private static final Object OBJ = new Object();

    //取钱的操作,约定每次取1000元
    public void withdraw() {
        //进行加同步锁
        synchronized (OBJ) {
            System.out.println(Thread.currentThread().getName() + "取钱前,余额为:" + balance);
            balance -= 1000;
            System.out.println(Thread.currentThread().getName() + "取了1000元后,余额为:" + balance);
        }
    }
}

/**
 * 取钱线程
 */
class PersonThread extends Thread {
    private BankAccount bankaccont;                //银行帐户


    public PersonThread(BankAccount bankaccont) {
        super();
        this.bankaccont = bankaccont;
    }

    @Override
    public void run() {
        //从帐户 中取钱
        bankaccont.withdraw();
    }
}