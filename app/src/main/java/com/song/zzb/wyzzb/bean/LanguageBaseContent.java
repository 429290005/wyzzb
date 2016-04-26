package com.song.zzb.wyzzb.bean;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by song on 2016/2/13.
 */
public class LanguageBaseContent {
    private Integer flagID;
    private BmobFile videofile;

    public Integer getCoursetype() {
        return coursetype;
    }

    public void setCoursetype(Integer coursetype) {
        this.coursetype = coursetype;
    }

    private Integer coursetype;
    public String getVideoauthor() {
        return videoauthor;
    }

    public void setVideoauthor(String videoauthor) {
        this.videoauthor = videoauthor;
    }

    public List<videocore> getVideocoreList() {
        return videocoreList;
    }

    public void setVideocoreList(List<videocore> videocoreList) {
        this.videocoreList = videocoreList;
    }

    public BmobFile getVideofile() {
        return videofile;
    }

    public void setVideofile(BmobFile videofile) {
        this.videofile = videofile;
    }

    public Integer getFlagID() {
        return flagID;
    }

    public void setFlagID(Integer flagID) {
        this.flagID = flagID;
    }

    public String getVideotitle() {
        return videotitle;
    }

    public void setVideotitle(String videotitle) {
        this.videotitle = videotitle;
    }

    private String videotitle;
    private String videoauthor;
    private List<videocore> videocoreList;
}
