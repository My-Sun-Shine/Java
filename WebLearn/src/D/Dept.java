package D;

/**
 * @Classname Dept
 * @Date 2020/3/11 16:19
 * @Created by Falling Stars
 * @Description
 */
public class Dept {
    private int deptNo;
    private String dname;
    private String loc;

    public int getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(int deptNo) {
        this.deptNo = deptNo;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public Dept(int deptNo, String dname, String loc) {
        super();
        this.deptNo = deptNo;
        this.dname = dname;
        this.loc = loc;
    }
}
