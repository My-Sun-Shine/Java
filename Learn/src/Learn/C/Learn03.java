package Learn.C;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Classname Learn03
 * @Date 2020/2/28 18:06
 * @Created by Falling Stars
 * @Description 定义方法,实现文件的复制,
 */
public class Learn03 {

    public static void main(String[] args) {
        String srcFile = "C:\\Users\\Falling Stars\\Desktop\\text.txt";
        String destFile1 = "C:\\Users\\Falling Stars\\Desktop\\text1.txt";
        String destFile2 = "C:\\Users\\Falling Stars\\Desktop\\text2.txt";
        copyfile(srcFile, destFile1);
        copyfile1(srcFile, destFile2);
    }

    /**
     * 定义方法,实现文件的复制, 把srcfile文件的内容复制到destFile文件中
     * 一次复制一个字节
     *
     * @param srcFile
     * @param destFile
     */
    private static void copyfile(String srcFile, String destFile) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            int cc = fis.read();
            while (cc != -1) {
                fos.write(cc);
                cc = fis.read();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    /**
     * 定义方法,实现文件的复制, 把srcfile文件的内容复制到destFile文件中
     * 一次复制一个数组
     * @param srcFile
     * @param destFile
     */
    private static void copyfile1(String srcFile, String destFile) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            byte[] bytes = new byte[1024];
            int len = fis.read(bytes);
            while (len != -1) {
                fos.write(bytes, 0, len);
                len = fis.read(bytes);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }
}
