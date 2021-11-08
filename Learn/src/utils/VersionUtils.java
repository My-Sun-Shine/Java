package utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Classname VersionUtils
 * @Date 2021/11/3 16:50
 * @Created by FallingStars
 * @Description 语义化版本控制规范 https://semver.org/lang/zh-CN/
 */
public class VersionUtils {

    private static final String split1 = "-";
    private static final String split2 = "\\.";
    private static final int limit = -1;

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1.0.0-beta.2");
        list.add("1.0.0-beta.11");
        list.add("1.0.0-alpha.1");
        list.add("1.0.0-alpha.beta");
        list.add("1.0.0");
        list.add("1.0.0-beta");
        list.add("2.1.1");
        list.add("2.1.0");
        list.add("1.0.0-alpha");
        list.add("2.0.0");
        list.add("1.0.0-rc.1");
        list.sort(new VersionComparator());
        for (String s : list) {
            System.out.println(s + " ：" + checkVersion(s));
        }
        System.out.println(checkVersion("1.0.0-x.7.z.92"));
        System.out.println(checkVersion("1.0.0.0-x.7.z.92"));
        System.out.println(checkVersion("1.0-x.7.z.92"));
        System.out.println(getAllVersion("1.0-x.7.z.92"));
    }


    /**
     * 检查是否符合语义化版本控制规范
     * <p>
     * 标准的版本号必须（MUST）采用 X.Y.Z 的格式，其中 X、Y 和 Z 为非负的整数，且禁止（MUST NOT）在数字前方补零
     * X 是主版本号、Y 是次版本号、而 Z 为修订号。每个元素必须（MUST）以数值来递增。例如：1.9.1 -> 1.10.0 -> 1.11.0
     * <p>
     * 先行版本号可以（MAY）被标注在修订版之后，先加上一个连接号再加上一连串以句点分隔的标识符来修饰。
     * 标识符必须（MUST）由 ASCII 字母数字和连接号 [0-9A-Za-z-] 组成，且禁止（MUST NOT）留白。
     * 数字型的标识符禁止（MUST NOT）在前方补零。先行版的优先级低于相关联的标准版本。被标上先行版本号则表示这个版本并非稳定而且可能无法满足预期的兼容性需求
     * 范例：1.0.0-alpha、1.0.0-alpha.1、1.0.0-0.3.7、1.0.0-x.7.z.92。
     *
     * @param str 版本
     * @return true 符合，false 不符合
     */
    public static boolean checkVersion(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        //分隔标准的版本号和先行版本号
        String[] arr = str.split(split1, limit);
        if (arr.length > 0) {
            //获取标准版本号，且不能超过3个，且不能小于2个
            String[] s1 = arr[0].split(split2, limit);
            if (s1.length > 3 || s1.length < 2) {
                return false;
            }
            for (String s : s1) {
                //标准版本号必须是非负整数，且禁止（MUST NOT）在数字前方补零
                if ("".equals(s) || !pattern.matcher(s).matches() || Long.parseLong(s) < 0
                        || !s.equals(String.valueOf(Integer.parseInt(s)))) {
                    return false;
                }
            }
        }
        if (arr.length > 1) {
            //不能为""
            if ("".equals(arr[1])) return false;
            //分隔先行版本号
            String[] s2 = arr[1].split(split2);
            for (String s : s2) {
                Pattern pattern2 = Pattern.compile("[0-9A-Za-z]*");
                if ("".equals(s) || !pattern2.matcher(s).matches()) {
                    return false;
                }
                //数字型的标识符禁止（MUST NOT）在前方补零
                if (pattern.matcher(s).matches() && !s.equals(String.valueOf(Integer.parseInt(s)))) {
                    return false;
                }
            }
        }
        //分隔标准版本号和先行版本号，不能超过两个
        return arr.length <= 2;
    }

    /**
     * 获取完整的版本号
     * 1.2 >> 1.2.0
     *
     * @param version 版本号
     * @return 完整版本号
     */
    public static String getAllVersion(String version) {
        String[] arr = version.split(split1, limit);
        String str = "";
        if (arr.length > 0) {
            String[] ss = arr[0].split(split2, limit);
            str = arr[0] + (ss.length == 2 ? ".0" : "");
        }
        if (arr.length > 1) str += split1 + arr[1];
        return str;
    }


    /**
     * 版本的优先层级指的是不同版本在排序时如何比较
     * <p>
     * 判断优先层级时，必须（MUST）把版本依序拆分为主版本号、次版本号、修订号及先行版本号后进行比较（版本编译信息不在这份比较的列表中）
     * 由左到右依序比较每个标识符，第一个差异值用来决定优先层级：主版本号、次版本号及修订号以数值比较，例如：1.0.0 < 2.0.0 < 2.1.0 < 2.1.1
     * <p>
     * 当主版本号、次版本号及修订号都相同时，改以优先层级比较低的先行版本号决定
     * 例如：1.0.0-alpha < 1.0.0
     * <p>
     * 有相同主版本号、次版本号及修订号的两个先行版本号，其优先层级必须（MUST）透过由左到右的每个被句点分隔的标识符来比较，直到找到一个差异值后决定：
     * 只有数字的标识符以数值高低比较，
     * 有字母或连接号时则逐字以 ASCII 的排序来比较。
     * 数字的标识符比非数字的标识符优先层级低。
     * 若开头的标识符都相同时，栏位比较多的先行版本号优先层级比较高。
     * 范例：1.0.0-alpha < 1.0.0-alpha.1 < 1.0.0-alpha.beta < 1.0.0-beta < 1.0.0-beta.2 < 1.0.0-beta.11 < 1.0.0-rc.1 < 1.0.0。
     *
     * @param o1 版本1
     * @param o2 版本2
     * @return o1>o2 return 1; o1=o2 return 0; o1<o2 return -1;
     */
    public static int compare(String o1, String o2) {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;
        if (o1.compareTo(o2) == 0) return 0;

        String[] o1Arr = o1.split(split1, limit);
        String[] o2Arr = o2.split(split1, limit);
        //没有先行版本号的版本，优先级高
        if (o1Arr.length == 1 && o2Arr.length == 2) {
            return 1;
        }
        if (o1Arr.length == 2 && o2Arr.length == 1) {
            return -1;
        }
        String[] version1Arr = o1Arr[0].split(split2, limit);
        String[] version2Arr = o2Arr[0].split(split2, limit);
        //主版本号、次版本号及修订号以数值比较
        for (int i = 0; i < 3; i++) {
            int v1 = version1Arr.length > i ? Integer.parseInt(version1Arr[i]) : 0;
            int v2 = version2Arr.length > i ? Integer.parseInt(version2Arr[i]) : 0;
            // 不相等则比较大小,分出结果
            if (v1 != v2) {
                return v1 < v2 ? -1 : 1;
            }
        }
        String[] version11Arr = o1Arr[1].split(split2, limit);
        String[] version22Arr = o2Arr[1].split(split2, limit);
        int len = Math.max(version11Arr.length, version22Arr.length);
        for (int i = 0; i < len; i++) {
            String v1 = version11Arr.length > i ? version11Arr[i] : "";
            String v2 = version22Arr.length > i ? version22Arr[i] : "";
            int result = compare2(v1, v2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    /**
     * 比较先行版本号
     *
     * @param v1 版本1
     * @param v2 版本2
     * @return 0 1 -1
     */
    public static int compare2(String v1, String v2) {
        Pattern pattern = Pattern.compile("[0-9]*");
        //若开头的标识符都相同时，栏位比较多的先行版本号优先层级比较高。
        if ("".equals(v1)) {
            return -1;
        }
        if ("".equals(v2)) {
            return 1;
        }
        boolean b1 = pattern.matcher(v1).matches();
        boolean b2 = pattern.matcher(v2).matches();
        int result;
        if (b1 && b2) {
            //只有数字的标识符以数值高低比较，
            result = Long.compare(Long.parseLong(v1), Long.parseLong(v2));
        } else if (b1) {
            result = -1;//数字的标识符比非数字的标识符优先层级低。
        } else if (b2) {
            result = 1;//数字的标识符比非数字的标识符优先层级低。
        } else {
            //有字母或连接号时则逐字以 ASCII 的排序来比较。
            result = v1.compareTo(v2);
        }
        return result;
    }


}


class VersionComparator implements Comparator<String> {

    /**
     * 如果返回 1  说明o1 > o2
     * <p>
     * 如果返回 0  说明o1 = o2
     * <p>
     * 如果返回 -1 说明o1 < o2
     *
     * @param o1 版本1
     * @param o2 版本2
     * @return 1 -1 0
     */
    @Override
    public int compare(String o1, String o2) {
        return VersionUtils.compare(o1, o2);
    }

}
