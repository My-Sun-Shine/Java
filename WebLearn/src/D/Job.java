package D;

/**
 * @Classname Job
 * @Date 2020/3/13 14:51
 * @Created by Falling Stars
 * @Description
 */
public class Job {
    private String dname = null;
    private String job = null;
    private int jobNum = 0;

    public Job(String dname, String job, int jobNum) {
        this.dname = dname;
        this.job = job;
        this.jobNum = jobNum;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getJobNum() {
        return jobNum;
    }

    public void setJobNum(int jobNum) {
        this.jobNum = jobNum;
    }
}
