package Learn.B;

import java.util.Arrays;

/**
 * @Classname Learn12
 * @Date 2020/2/26 14:42
 * @Created by Falling Stars
 * @Description Array工具类
 */
public class Learn12 {

    public static void main(String[] args) {
        int[] data = {12, 93, 82, 5, 70, 63};

        //把数组a中的元素转换为字符串
        System.out.println(Arrays.toString(data));

        //数组的复制
        data = Arrays.copyOf(data, data.length + 2);

        //排序
        Arrays.sort(data);
        System.out.println(Arrays.toString(data));

        //把a数组[fromindex, toIndex)范围的元素全用Val代替
        Arrays.fill(data, 1, 5, 10);
        System.out.println(Arrays.toString(data));

        //用二分查找算法在a数组中查找key元素,返回元素在数组中的索引值, 如果不存在key元素返回负数
        System.out.println(Arrays.binarySearch(data, 93));
    }

}
