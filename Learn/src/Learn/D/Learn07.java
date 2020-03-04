package Learn.D;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Arrays;

/**
 * @Classname Learn07
 * @Date 2020/3/4 21:23
 * @Created by Falling Stars
 * @Description 通过注解和反射自定义建表
 */
public class Learn07 {
    public static void main(String[] args) {
        StringBuilder sql = new StringBuilder();
        sql.append("create table ");
        String tableName = "";
        try {
            Class user = Class.forName("Learn.D.User");
            System.out.println(user);
            Annotation[] annotations = user.getAnnotations();
            System.out.println(Arrays.toString(annotations));
            //判断类上是否有Table注解
            if (user.isAnnotationPresent(Table.class)) {
                Table table = (Table) user.getAnnotation(Table.class);
                tableName = table.name();
                System.out.println(tableName);
                sql.append(tableName + "(");

            }

            Field[] fields = user.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    String name = column.name();
                    String type = column.type();
                    int length = column.length();
                    String constraint = column.constraint();
                    System.out.println(name + "," + type + "," + length + "," + constraint);
                    sql.append(name + " " + type + "(" + length + ") " + constraint + ",");
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String createSql = sql.substring(0, sql.length() - 1) + ")";
        System.out.println(createSql);
        createTable(createSql,tableName);
    }

    private static void createTable(String sql, String tableName) {

        String url = "jdbc:mysql://localhost:3306/bjpow";
        String sql1 = "drop table if exists " + tableName;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "123456");
            ps = conn.prepareStatement(sql1);
            ps.execute();

            ps = conn.prepareStatement(sql);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


@Table(name = "tbl_user2")
class User {

    @Column(name = "id", type = "int", constraint = "primary key")
    private int id;

    @Column(name = "name", type = "varchar", length = 255, constraint = "not null")
    private String name;

    // 带有这个注解的表示参加自动建表功能。
    @Column(name = "password", type = "varchar", length = 255, constraint = "not null")
    private String password;

    // 没有这个Column注解的则表示不参加自动建表功能。
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Table {
    String name();
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Column {
    /**
     * 字段名
     *
     * @return
     */
    String name();

    /**
     * 字段的数据类型
     *
     * @return
     */
    String type();

    /**
     * 字段长度
     *
     * @return
     */
    int length() default 10;  // 给属性默认赋值10

    /**
     * 字段约束
     *
     * @return
     */
    String constraint();
}