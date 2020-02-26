package Learn.B;

import java.util.Arrays;

/**
 * @Classname Learn11
 * @Date 2020/2/26 13:40
 * @Created by Falling Stars
 * @Description 数组算法
 */
public class Learn11 {
    public static void main(String[] args) {
        int[] data = getArray();
        System.out.println("原数组：" + Arrays.toString(data));
        reverse(data);//反转
        System.out.println("反转数组：" + Arrays.toString(data));
        System.out.println("MaxIndex：" + getMaxIndex(data));
        sort(data);//冒泡排序
        System.out.println("排序数组：" + Arrays.toString(data));
        data = getArray();
        System.out.println("原数组：" + Arrays.toString(data));
        selectSort(data);//选择排序
        System.out.println("排序数组：" + Arrays.toString(data));
        int[] data1 = {5, 12, 63, 70, 82, 93};
        System.out.println("原数组：" + Arrays.toString(data1));
        System.out.println(indexOf(data1, 5));
        System.out.println(indexOf(data1, 93));
        System.out.println(indexOf(data1, 50));
        System.out.println(indexOf(data1, 70));
    }

    /**
     * 定义一个方法, 返回一个整数数组, 要求对数组元素进行随机的初始化[0,100)范围内的数据,
     * Math.random() 产生[0,1) 范围内的随机小数
     *
     * @return 数组
     */
    private static int[] getArray() {
        int[] data = new int[10];

        for (int i = 0; i < data.length; i++) {
            data[i] = (int) (Math.random() * 100);
        }
        return data;
    }

    /**
     * 实现数组的逆序
     *
     * @param array 原数组
     */
    private static void reverse(int[] array) {
        for (int i = 0; i <= array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - 1 - i] = temp;
        }
    }

    /**
     * 返回指定数组中最大元素的索引值
     *
     * @param array 原数组
     * @return 索引
     */
    private static int getMaxIndex(int[] array) {
        int maxNum = array[0];
        int index = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxNum) {
                maxNum = array[i];
                index = i;
            }
        }
        return index;
    }

    /**
     * 冒泡排序
     *
     * @param array 原数组
     */
    private static void sort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 选择排序
     *
     * @param array 原数组
     */
    private static void selectSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int min = i;
            for (int j = i; j < array.length; j++) {
                if (array[j] < array[min]) {
                    min = j;
                }
            }
            int temp = array[i];
            array[i] = array[min];
            array[min] = temp;
        }
    }

    /**
     * 二分查找算法，也称为折半查找算法，前提是数组由小到大排序
     *
     * @param array 原数组
     * @param key   查找元素
     * @return 索引
     */
    private static int indexOf(int[] array, int key) {
        int from = 0;
        int to = array.length - 1;
        int mide = (from + to) / 2;
        while (from <= to) {
            if (array[mide] < key) {
                from = mide + 1;
            } else if (array[mide] > key) {
                to = mide - 1;
            } else {
                return mide;
            }
            mide = (from + to) / 2;
        }
        return -1;
    }


}
