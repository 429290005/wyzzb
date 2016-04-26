package com.song.zzb.wyzzb.bean;

/**
 * Created by song on 2016/2/6.
 * 章节内容
 */
public class listContent {
    private String listchapter;//第1节
    private String listchapterdsc;//一元函数导数
    private String url;
    private String flag;//对应MathBaseContent中的chapterAct字段。代表具体小节里的内容
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getListchapterdsc() {
        return listchapterdsc;
    }

    public void setListchapterdsc(String listchapterdsc) {
        this.listchapterdsc = listchapterdsc;
    }

    public String getListchapter() {
        return listchapter;
    }

    public void setListchapter(String listchapter) {
        this.listchapter = listchapter;
    }


}
