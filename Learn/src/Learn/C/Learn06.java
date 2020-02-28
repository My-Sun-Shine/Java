package Learn.C;

import java.io.*;

/**
 * @Classname Learn06
 * @Date 2020/2/28 21:39
 * @Created by Falling Stars
 * @Description BufferedReader读取文件/BufferedWriter保存数据到文件中
 */
public class Learn06 {
    public static void main(String[] args) throws IOException {
        String readUrl = "C:\\Users\\Falling Stars\\Desktop\\text.txt";
        read(readUrl);		//使用BufferedReader读取文件
        String writerUrl = "C:\\Users\\Falling Stars\\Desktop\\text1.txt";

        writer(writerUrl);		//使用BufferedWriter保存数据到文件中
    }

    /**
     * 使用BufferedReader读取文件
     * @param readUrl
     * @throws IOException
     */
    private static void read(String readUrl) throws IOException {
        //建立字符流通道
        Reader in = new FileReader(readUrl);
        //对字符流进行缓冲
        BufferedReader br = new BufferedReader(in);

        //可以从字符流中一次读取一行, 读到文件末尾返回null
        String line = br.readLine();
        while( line != null ){
            System.out.println( line );
            line = br.readLine();
        }

        br.close();
    }


    /**
     * 使用BufferedWriter保存数据到文件中
     * @param writerUrl
     * @throws IOException
     */
    private static void writer(String writerUrl) throws IOException {
        //建立字符流通道
        FileWriter out = new FileWriter(writerUrl, true );
        //对字符流进行缓冲
        BufferedWriter bw = new BufferedWriter(out);

        //使用缓冲流时,是把数组保存到缓冲区中 , 并没有立即保存到文件中
        bw.write("这是使用字符缓冲流写入的数据");

        //bw.flush(); 		//会把缓冲区的数据清空到文件中
        bw.close();
    }



}
