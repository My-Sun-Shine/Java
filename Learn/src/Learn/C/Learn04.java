package Learn.C;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Classname Learn04
 * @Date 2020/2/28 20:55
 * @Created by Falling Stars
 * @Description FileReader/FileWriter只能读写纯文本文件
 */
public class Learn04 {
    public static void main(String[] args) {
        String readUrl = "C:\\Users\\Falling Stars\\Desktop\\text.txt";
        read1(readUrl);
        System.out.println();
        System.out.println("---------------------------------");
        read2(readUrl);
        String writerUrl = "C:\\Users\\Falling Stars\\Desktop\\text1.txt";
        writer(writerUrl);

    }

    /**
     * 1)	FileReader/FileWriter只能读写纯文本文件
     * 2)	FileReader/FileWrite读写的文本文件编码要与当前环境编码兼容
     */
    private static void read1(String readUrl) {
        FileReader fr = null;
        try {
            fr = new FileReader(readUrl);
            int cc = fr.read();
            //read()一次读取一个字符, 返回字符的码值, 读到文件末尾返回-1
            while (cc != -1) {
                System.out.print((char) cc);
                cc = fr.read();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 一次读取一个数组
     */
    private static void read2(String readUrl) {
        FileReader fr = null;
        try {
            fr = new FileReader(readUrl);
            char[] contents = new char[1024];
            int len = fr.read(contents);
            //read()一次读取一个字符, 返回字符的码值, 读到文件末尾返回-1
            while (len != -1) {
                System.out.print(new String(contents, 0, len));
                len = fr.read(contents);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static void writer(String writerUrl) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(writerUrl);

            //一次写一个字符
            fw.write(97);        //字符的码值
            fw.write(30028);
            fw.write('汉');
            fw.write("\r\n");

            //一次写一个字符数组
            char[] contents = "中国加油，武汉加油".toCharArray();
            fw.write(contents);
            fw.write("\r\n");

            //一次写一个字符串
            fw.write("一次写一个字符串");

            //换行, 在中文Windows系统中,换行需要两个字符\r\n
            fw.write("\r\n");

            //一次写字符数组的部分字符
            fw.write(contents);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fw!=null){
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
