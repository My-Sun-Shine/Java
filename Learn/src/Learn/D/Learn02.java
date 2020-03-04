package Learn.D;

/**
 * @Classname Learn02
 * @Date 2020/3/3 23:11
 * @Created by Falling Stars
 * @Description 怎么获取类路径下的文件的绝对路径
 */
public class Learn02 {
    /**
     * 放在src目录下的资源都可以认为放到了类路径当中。
     * 怎么获取类路径下的文件的绝对路径？
     * String path = Thread.currentThread().getContextClassLoader().getResource("从类的根路径下作为起点的路径").getPath();
     * 以上代码只能获取类路径下文件的绝对路径，当这个文件不在类路径下的话，以上代码无法获取绝对路径
     * Thread.currentThread().getContextClassLoader() 获取当前线程的类加载器对象
     *
     * @param args
     */
    public static void main(String[] args) {
        // 当一个文件在类路径下的时候，怎么获取该文件的绝对路径呢？
        // String path = Thread.currentThread().getContextClassLoader().getResource("a").getPath();
        String url = "Learn/D/Learn02.class";
        String path = Thread.currentThread().getContextClassLoader().getResource(url).getPath();
        System.out.println(path);
    }
}
