package Learn.B;

/**
 * @Classname Learn01
 * @Date 2020/2/24 15:01
 * @Created by Falling Stars
 * @Description Object类型
 */
public class Learn01 {
    private String name;

    private Learn01(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Learn01 learn01 = new Learn01("aaa");
        System.out.println(learn01.getClass().getName());
        System.out.println(learn01.hashCode());
        System.out.println(Integer.toHexString(learn01.hashCode()));
        System.out.println(learn01.toString());//重写toString方法

        System.out.println();
        Learn01 learn011 = new Learn01("aaa");
        Learn01 learn012 = learn01;
        System.out.println(learn01.equals(learn011));
        System.out.println(learn01.equals(learn012));
        System.out.println(learn01 == learn011);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getClass().toString();
    }


    @Override
    public boolean equals(Object obj) {
        //如果两个变量引用引用同一对象，返回true
        if (this == obj)
            return true;

        //如果参数对象为null，返回false
        if (obj == null)

            return false;

        //如果两对象的类型不一样，返回false
        if (getClass() != obj.getClass())
            return false;

        Learn01 learn = (Learn01) obj;
        if (this.name == null) {
            if (learn.name != null) {
                return false;
            }
        } else if (!this.name.equals(learn.name)) {
            return false;
        }

        return true;
    }
}
