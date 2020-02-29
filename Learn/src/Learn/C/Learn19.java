package Learn.C;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Classname Learn19
 * @Date 2020/2/29 15:43
 * @Created by Falling Stars
 * @Description Timer对象：计时器
 */
public class Learn19 {
    public static void main(String[] args) {
        //创建Timer对象
        //Timer timer = new Timer();
        Timer timer = new Timer( true 	); 		//把定时器设置为守护线程

        //timer.schedule(task, time);  	//在指定的时间time后, 执行task任务

        //timer.schedule(task, delay);    //在delay毫秒后, 执行task任务

        //timer.schedule(task, firstTime, period);
        // 在firstTime时间执行task任务,以后每隔period毫秒就再执行一次
        // 在5s时间之后执行task任务，每隔1000ms执行一次
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println( new Date() );
            }
        }, 5000, 1000);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main....end...." );
        //main线程结束之后，守护线程不再执行
    }
}
