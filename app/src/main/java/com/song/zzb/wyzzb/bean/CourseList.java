package com.song.zzb.wyzzb.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by song on 2016/2/11.
 */
public class CourseList extends BmobObject {
    private String title;

    public Integer getFlagID() {
        return flagID;
    }

    public void setFlagID(Integer flagID) {
        this.flagID = flagID;
    }

    private Integer flagID;
    public String getTitledsc() {
        return titledsc;
    }

    public void setTitledsc(String titledsc) {
        this.titledsc = titledsc;
    }

    public BmobFile getPicFile() {
        return picFile;
    }

    public void setPicFile(BmobFile picFile) {
        this.picFile = picFile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String titledsc;
    private BmobFile picFile;
}
