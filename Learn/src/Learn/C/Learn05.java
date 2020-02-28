package Learn.C;

import java.io.*;

/**
 * @Classname Learn05
 * @Date 2020/2/28 21:38
 * @Created by Falling Stars
 * @Description 转换流类:InputStreamReader可以把字节流转换为字符流,OutputStreamWriter可以把字符流转换为字节流
 */
public class Learn05 {

    /**
     * 文本文件编码与当前环境编码兼容时,使用FileReader/FielWriter读写,
     * 当文本文件编码与当前环境编码 不 兼容时, 使用InputStreamReader/OutputStreamWriter
     * 这两个流类称为转换流类
     * InputStreamReader可以把字节流转换为字符流
     * OutputStreamWriter可以把字符流转换为字节流
     *
     * @param args
     */
    public static void main(String[] args) {
        String inputUrl = "C:\\Users\\Falling Stars\\Desktop\\text.txt";
        input(inputUrl) ; 			//以指定的编码格式读取文本文件
        String outputUrl = "C:\\Users\\Falling Stars\\Desktop\\text1.txt";
        output(outputUrl);        //以指定的编码把数据保存到文件中
    }

    /**
     * InputStreamReader可以把字节流转换为字符流
     * @param inputUrl
     */
    private static void input(String inputUrl) {
        //InputStream是一个抽象类, 需要赋值子类对象, FileInputStream继承了InputStream
        //InputStream in = new FileInputStream(inputUrl);
        FileInputStream in = null;
        InputStreamReader isr = null;
        try {
            //创建字节流
            in = new FileInputStream(inputUrl);
            //以GBK的编码读取in字节流中的数据,
            isr = new InputStreamReader(in, "UTF-8");
            int cc = isr.read();
            while (cc != -1) {
                System.out.print((char) cc);
                cc = isr.read();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * OutputStreamWriter可以把字符流转换为字节流
     * @param outputUrl
     */
    private static void output(String outputUrl) {
        //向d:/abc.txt文件中保存数据, 该文件是以GBK编码
        OutputStream out = null;
        OutputStreamWriter osw = null;
        try {
            out = new FileOutputStream(outputUrl, true);
            //以指定的编码把字符转换为字节流
            osw = new OutputStreamWriter(out, "GBK");
            osw.write("IDEA环境使用UTF8编码, 把数据保存到GBK文件中");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }


}
