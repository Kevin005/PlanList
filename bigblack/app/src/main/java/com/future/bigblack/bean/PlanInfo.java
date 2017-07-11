package com.future.bigblack.bean;

/**
 * Created by kevin on 17-7-2.
 */

public class PlanInfo {
    private int id;
    private String content;
    /** 1:ing 0:ed */
    private int is_doing;
    /** 1:普通 2:重要 */
    private int level;
    /** 12345678900 */
    private long dateStamp;
    /** 2017-01-01 */
    private String dateDay;
    /** 类型:0:正常不显示,1:工作 */
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(long dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getDateDay() {
        return dateDay;
    }

    public void setDateDay(String dateDay) {
        this.dateDay = dateDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIs_doing() {
        return is_doing;
    }

    public void setIs_doing(int is_doing) {
        this.is_doing = is_doing;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
