package com.song.zzb.wyzzb.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by song on 2016/1/29.
 */
public class News extends BmobObject {

    private Integer clicksum;
    private String papercontent;
    private  String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getClicksum() {
        return clicksum;
    }

    public void setClicksum(Integer clicksum) {
        this.clicksum = clicksum;
    }

    public String getPaperauthor() {
        return paperauthor;
    }

    public void setPaperauthor(String paperauthor) {
        this.paperauthor = paperauthor;
    }

    public String getPapercontent() {
        return papercontent;
    }

    public void setPapercontent(String papercontent) {
        this.papercontent = papercontent;
    }

    public String getPapertype() {
        return papertype;
    }

    public void setPapertype(String papertype) {
        this.papertype = papertype;
    }

    private String paperauthor;
    private String papertype;


}
