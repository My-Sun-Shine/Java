package Learn.B;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Classname Learn06
 * @Date 2020/2/25 20:25
 * @Created by Falling Stars
 * @Description 方法覆盖中的异常处理
 */
public class Learn06 {

    public void m() throws IOException {

    }
}

/**
 * 父类方法抛出了异常,重写后,可以抛出相同的异常
 * 也可以抛出子异常
 * 子类方法,也可以不抛出异常
 */

class Learn060 extends Learn06 {
    @Override
    public void m() throws IOException {

    }
}

class Learn061 extends Learn06 {
    @Override
    public void m() throws FileNotFoundException {

    }
}

class Learn062 extends Learn06 {
    @Override
    public void m() {

    }
}