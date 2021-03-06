package com.song.zzb.wyzzb.bean;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by song on 2016/2/2.
 */
public class MathBase extends BmobObject{
    private Integer ordeby;
    private String chapter;
    private Integer coursetype;
    private String chaptercontent;
    private String chapterdsc;
    private List<listContent> listContent;
    public Integer getCoursetype() {
        return coursetype;
    }
    public void setCoursetype(Integer coursetype) {
        this.coursetype = coursetype;
    }
    public String getChaptercontent() {
        return chaptercontent;
    }
    public void setChaptercontent(String chaptercontent) {
        this.chaptercontent = chaptercontent;
    }
    public List<listContent> getListContent() {
        return listContent;
    }
    public void setListContent(List<listContent> listContent) {
        this.listContent = listContent;
    }
    public Integer getOrdeby() {
        return ordeby;
    }
    public void setOrdeby(Integer ordeby) {
        this.ordeby = ordeby;
    }
    public String getChapter() {
        return chapter;
    }
    public void setChapter(String chapter) {
        this.chapter = chapter;
    }
    public String getChapterdsc() {
        return chapterdsc;
    }
    public void setChapterdsc(String chapterdsc) {
        this.chapterdsc = chapterdsc;
    }
}