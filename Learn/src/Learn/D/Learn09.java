package Learn.D;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

/**
 * @Classname Learn09
 * @Date 2020/3/7 15:36
 * @Created by Falling Stars
 * @Description 使用DOM4j读取XML文档，使用XPath解析遍历XML文档
 */
public class Learn09 {

    public static void main(String[] args) {
        m1();
        System.out.println("------------------------------");
        m2();
        System.out.println("------------------------------");
        m3();

    }

    /**
     * 使用DOM4j进行XML文档读取
     */
    private static void m1() {
    /*
    1.SAXReader:[XML文档读取器]
    2.Document:管理在内存中生成的Dom树。相当于JavaScript使用document
    3.Element: 管理Dom树中dom对象   Element对象就相当于dom对象
    4.Attribute:管理Dom对象中属性
     */
        //创建一个【XML文档读取器对象】
        SAXReader saxReader = new SAXReader();
        try {
            //xml文档加载到内存中
            Document document = saxReader.read("src/Learn/D/Learn08.xml");
            //读取Dom树根目录标签
            Element rootElement = document.getRootElement();

            String typeName = rootElement.getName();
            System.out.println("根目录标签类型名称 " + typeName);

            List<Element> elements = rootElement.elements();
            for (Element element : elements) {
                System.out.println("根目录下子标签类型名 " + element.getName());
                List<Attribute> attributes = element.attributes();
                for (Attribute attribute : attributes) {
                    System.out.println(attribute);
                }
            }

            String value = rootElement.attributeValue("name");
            System.out.println("根目录标签下name属性的值 " + value);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    /**
     * 遍历XML文档读取每一个标签中name属性值
     */
    private static void m2() {
        SAXReader saxObj = new SAXReader();
        try {
            Document document = saxObj.read("src/Learn/D/Learn08.xml");
            Element rootDom = document.getRootElement();
            parse(rootDom, "name");
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    /**
     * 遍历
     *
     * @param e        元素
     * @param attrName 属性名
     */
    private static void parse(Element e, String attrName) {
        String value = e.attributeValue(attrName);
        System.out.println("当前标签的" + attrName + "属性：" + value);

        //子标签
        List<Element> sonList = e.elements();
        if (sonList != null && sonList.size() > 0) {
            for (Element son : sonList) {
                parse(son, attrName);
            }
        }

    }


    /**
     * 使用XPath解析XML文档数据
     */
    private static void m3() {
        SAXReader saxObj = new SAXReader();
        try {
            Document document = saxObj.read("src/Learn/D/Learn08.xml");
            String xpath1 = "//@name";//获取所有的name属性
            List<Attribute> attrlist = document.selectNodes(xpath1);
            for (Attribute attr : attrlist) {
                String value = attr.getValue();
                System.out.println("name属性值：" + value);
            }
            System.out.println();
            String xpath2 = "//Student[@age>25]";//所有age属性大于等于25的学员标签
            List<Element> elementList = document.selectNodes(xpath2);
            for (Element element : elementList) {
                String name = element.attributeValue("name");
                String age = element.attributeValue("age");
                System.out.println("学员姓名 " + name + " 学员年龄 " + age);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
