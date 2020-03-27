package com.crm.workbench.domain;

/**
 * @Classname ClueActivityRelation
 * @Date 2020/3/27 18:58
 * @Created by Falling Stars
 * @Description 市场活动和线索关联
 */
public class ClueActivityRelation {
    private String id;
    private String clueId;
    private String activityId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClueId() {
        return clueId;
    }

    public void setClueId(String clueId) {
        this.clueId = clueId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
