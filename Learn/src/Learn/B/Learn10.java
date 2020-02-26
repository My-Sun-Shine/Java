package Learn.B;

import java.util.Arrays;

/**
 * @Classname Learn10
 * @Date 2020/2/26 13:15
 * @Created by Falling Stars
 * @Description 二维数组
 */
public class Learn10 {
    public static void main(String[] args) {
        int[] data1 = new int[]{1, 2, 3, 4, 5};
        int[] data2 = {4, 5, 6, 7};
        int x = 20;
        int y = 21;
        int[] data3 = {x, y};

        int[][] myData = {data1, data2, data3};
        for (int i = 0; i < myData.length; i++) {
            for (int j = 0; j < myData[i].length; j++) {
                System.out.print(myData[i][j] + "\t");
            }
            System.out.println();
        }

        // 定义一个长度为5的二维数组
        int[][] myData2 = new int[5][];
        for (int i = 0; i < myData2.length; i++) {
            System.out.println(myData2[i]);
        }

        // 5行4列的二维数组
        int[][] myData3 = new int[5][4];
        //Arrays.deepToString() 可以把多维数组中的元素转换为字符串
        System.out.println(Arrays.deepToString(myData3));

        //给二维数组的元素赋值, 只要是int[]类型的数据就可以赋值
        myData3[0] = data1;
        myData3[2] = data2;
        System.out.println(Arrays.deepToString(myData3));

        //初始化
        int[][] myData4 = new int[][]{data1, data2, data3};
        int[][] myData5 = new int[][]{data1, data2, new int[]{1,2,3}};

    }
}
