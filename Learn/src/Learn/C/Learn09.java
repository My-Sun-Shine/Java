package Learn.C;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Classname Learn09
 * @Date 2020/2/29 11:37
 * @Created by Falling Stars
 * @Description 创建线程的 方式三 实现Callable接口
 */
public class Learn09 {
    /**
     * Callable接口的call()方法有返回值, Runnable接口的run()方法没有返回值
     * Callable接口经常应用于线程池中
     *
     * @param args
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Callable09 c = new Callable09();            //创建Callable接口对象

        ////FutureTask类实现了RunnableFuture接口, RunnableFuture接口继承了Runnable接口
        FutureTask<Integer> task = new FutureTask<>(c);

        //FutureTask类就是Runnable接口的实现类
        Thread t3 = new Thread(task);
        //开启线程
        t3.start();

        System.out.println("在main线程中,可以获得子线程返回的数据:" + task.get());
    }
}

/**
 * 定义类实现Callable接口, Callable接口通过泛型指定call()方法返回值类型
 */
class Callable09 implements Callable<Integer> {
    //call()方法有返回值, 声明抛出了异常
    @Override
    public Integer call() {
        //子线程要执行的代码
        Integer xx = new Random().nextInt(100);
        System.out.println("在子线程中产生正整数:" + xx);
        return xx;
    }
}