package Learn.B;

import java.util.LinkedList;

/**
 * @Classname Learn21
 * @Date 2020/2/28 11:47
 * @Created by Falling Stars
 * @Description LinkedList双向链表
 */
public class Learn21 {
    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<>();

        linkedList.add("xx");
        linkedList.add("yy");
        linkedList.add("zz");
        System.out.println(linkedList);    //[xx, yy, zz]

        //在头部/尾部添加
        linkedList.addFirst("aa");
        System.out.println(linkedList);    //[aa, xx, yy, zz]
        linkedList.addLast("bb");
        System.out.println(linkedList);    //[aa, xx, yy, zz, bb]

        //删除第一个/删除最后一个
        linkedList.removeFirst();
        System.out.println(linkedList);    //[xx, yy, zz, bb]
        linkedList.removeLast();
        System.out.println(linkedList);    //[xx, yy, zz]

        //返回第一个/最后一个
        System.out.println(linkedList.getFirst());  //xx
        System.out.println(linkedList.getLast());   //zz
        System.out.println(linkedList.peek());        //xx 返回第一个元素
        System.out.println(linkedList.peekLast());    //zz 返回最后一个元素

        //使用push()/pop()模拟栈
        //栈的特点, 后进先出
        linkedList.push("hh");        //压栈,入栈, 把元素添加到列表的头部
        System.out.println(linkedList);          //[hh, xx, yy, zz]
        System.out.println(linkedList.pop());    //hh 弹栈,出栈, 把列表的第一个元素删除并返回
        System.out.println(linkedList);          //[xx, yy, zz]

        //使用offer()/poll()模拟队列
        //队列特点: 先进先出
        linkedList.offer("mm");            //入队, 把元素添加到列表的尾部
        System.out.println(linkedList);         //[xx, yy, zz, mm]
        System.out.println(linkedList.poll());  //xx 出队, 把列表的第一个元素删除并返回
        System.out.println(linkedList);         //[yy, zz, mm]
    }

}
