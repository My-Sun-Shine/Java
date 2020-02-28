package Learn.C;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Classname Learn02
 * @Date 2020/2/28 18:00
 * @Created by Falling Stars
 * @Description FileOutputStream字节流写入数据
 */
public class Learn02 {
    public static void main(String[] args) throws IOException {
        String str="C:\\Users\\Falling Stars\\Desktop\\text.txt";
        //1)建立与文件的流通道
        //如果文件不存在,系统会创建一个新的文件, 如果文件已存在,会覆盖文件原来的内容
        //FileOutputStream fos = new FileOutputStream(str);			//以覆盖的方式打开 文件
        //如果文件 不存在就创建文件, 如果文件已存在, 把新写入的内容追加到文件末尾
        FileOutputStream fos = new FileOutputStream(str, true);   //以追加的方式

        fos.write('\n');
        //2)把数组以字节为单位保存到文件中
        fos.write(97);        //一次写一个字节
        fos.write(98);
        fos.write(99);

        //一次写一个字节数组
        byte[] bytes = "\nhello,world".getBytes();
        fos.write(bytes);

        //把字节数组的部分字节保存到文件中
        fos.write(bytes, 0, 6);        //把bytes字节数组中从0开始的5个字节保存到文件中

        //3)关闭流通道
        fos.close();
    }


}
