package Learn.B;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Classname Learn26
 * @Date 2020/2/28 16:14
 * @Created by Falling Stars
 * @Description Map集合的练习
 */
public class Learn26 {
    public static void main(String[] args) {
        m1();
    }

    /**
     * String url = "https://image.baidu.com/search/index? tn=baiduimage& ct=201326592 &lm=-1&cl=2&ie=gb18030
     * &word=asdfaf&fr=ala&ala=1& alatpl=adress&pos=0& hs=2& xthttps=111111";
     * 把url中的参数名与参数值保存到Map中
     * 遍历map打印参数与参数值
     */
    private static void m1() {
        String url = "https://image.baidu.com/search/index? tn=baiduimage& ct=201326592 &lm=-1&cl=2&ie=gb18030 " +
                "&word=asdfaf&fr=ala&ala=1& alatpl=adress&pos=0& hs=2& xthttps=111111";
        int index = url.indexOf("?");
        url = url.substring(index + 1);
        String[] strs = url.split("[&]");

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            strs[i] = strs[i].trim();
            map.put(strs[i].split("[=]")[0], strs[i].split("[=]")[1]);
        }
        System.out.println(Arrays.toString(strs));
        System.out.println(map);
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry);
        }


    }
}
