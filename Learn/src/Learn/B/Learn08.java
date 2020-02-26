package Learn.B;

import java.util.Arrays;

/**
 * @Classname Learn08
 * @Date 2020/2/25 21:28
 * @Created by Falling Stars
 * @Description 数组的定义、赋值、访问、扩容、插入、和可变长参数
 */
public class Learn08 {
    public static void main(String[] args) {
        // 数组静态初始化，不需要指定数组的长度，数组的长度有初始化元素个数来决定
        int[] data1 = new int[]{123, 13, 11, 22, 2};
        // 可以简化为,这种简化的形式只能用于数组的静态初始化，不能进行数组的赋值
        int[] data2 = {1, 2, 3, 4, 5};
        // data1 = {1, 2, 3, 4, 5};//报错

        // 循环输出
        for (int x : data1) {
            System.out.println(x);
        }

        System.out.println(Arrays.toString(data2));

        System.out.println();
        // 可变长参数
        System.out.println(sum());
        System.out.println(sum(1, 2, 3, 4, 5, 6));
        System.out.println(sum(data2));


        System.out.println();
        // 数组扩容
        m1();
        m2();
        m3();

        System.out.println();
        // 数组的插入
        System.out.println(Arrays.toString(insert(data1, 0, 0)));
        System.out.println(Arrays.toString(insert(data1, 2, 100)));
        System.out.println(Arrays.toString(insert(data1, -1, 0)));
        System.out.println(Arrays.toString(insert(data1, 5, 80)));
        System.out.println(Arrays.toString(insert(data1, 6, 0)));
        // 数组删除
        System.out.println();
        System.out.println(Arrays.toString(delete1(data1, 6)));
        System.out.println(Arrays.toString(delete1(data1, -1)));
        System.out.println(Arrays.toString(delete1(data1, 0)));
        System.out.println(Arrays.toString(delete1(data1, 3)));
        System.out.println(Arrays.toString(delete1(data1, 4)));
        System.out.println();
        System.out.println(Arrays.toString(delete2(data1, 6)));
        System.out.println(Arrays.toString(delete2(data1, -1)));
        System.out.println(Arrays.toString(delete2(data1, 0)));
        System.out.println(Arrays.toString(delete2(data1, 3)));
        System.out.println(Arrays.toString(delete2(data1, 4)));

    }

    /**
     * 可变长参数，使用...表示
     *
     * @param num
     * @return
     */
    private static int sum(int... num) {
        int sumNum = 0;
        for (int x : num) {
            sumNum += x;
        }
        return sumNum;
    }

    /**
     * 数组扩容
     */
    private static void m1() {
        int[] data = {1, 2, 3, 4};
        int[] newData = new int[data.length + 2];
        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i];
        }

        data = newData;

        System.out.println(Arrays.toString(data));
    }

    /**
     * 数组扩容
     */
    private static void m2() {
        int[] data = {1, 2, 3, 4};
        int[] newData = new int[data.length + 2];

        // 把src数组中从Srcpos开始的length元素复制到dest数组的destPos开始的位置
        // System.arraycopy(src, srcPos, dest, destPos, length);
        System.arraycopy(data, 0, newData, 0, data.length);
        //JNI, Java Native Interface, 通过JNI技术,可以在Java中调用其他语言编写的代码

        data = newData;

        System.out.println(Arrays.toString(data));
    }

    /**
     * 数组扩容
     */
    private static void m3() {
        int[] data = {1, 2, 3, 4};

        // 返回一个新数组
        // Arrays.copyOf( 源数组, 新数组的长度 )
        data = Arrays.copyOf(data, data.length + 2);


        System.out.println(Arrays.toString(data));
    }


    /**
     * 插入数组元素
     *
     * @param datas 数组
     * @param index 索引
     * @param data  插入数据
     * @return
     */
    private static int[] insert(int[] datas, int index, int data) {
        if (index < 0 || index > datas.length) {
            return null;
        }
        int[] newDatas = new int[datas.length + 1];
        for (int i = 0; i < index; i++) {
            newDatas[i] = datas[i];
        }
        newDatas[index] = data;
        for (int i = index; i < datas.length; i++) {
            newDatas[i + 1] = datas[i];
        }

        return newDatas;
    }

    private static int[] delete1(int[] datas, int index) {
        if (index < 0 || index >= datas.length) {
            return datas;
        }
        int[] newDatas = new int[datas.length - 1];
        for (int i = 0; i < index; i++) {
            newDatas[i] = datas[i];
        }
        for (int i = index + 1; i < datas.length; i++) {
            newDatas[i - 1] = datas[i];
        }
        return newDatas;
    }

    private static int[] delete2(int[] datas, int index) {
        if (index < 0 || index >= datas.length) {
            return datas;
        }
        int[] newDatas = new int[datas.length - 1];
        System.arraycopy(datas, 0, newDatas, 0, index);
        System.arraycopy(datas, index + 1, newDatas, index, datas.length - index - 1);

        return newDatas;
    }
}
