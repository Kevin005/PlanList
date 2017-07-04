package com.future.bigblack.bean;

/**
 * Created by kevin on 17-7-2.
 */

public class PlanInfo {
    private int id;
    private String content;
    private int is_doing;
    /** 1:普通 2:重要 */
    private int level;
    private long date;

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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
