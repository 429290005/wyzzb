package com.song.zzb.wyzzb.bean;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by song on 2016/2/12.
 */
public class MathBaseContent extends BmobObject  {

    private BmobFile videofile;
    private Integer chapterAct;
    private Integer chapter;
    private Integer coursetype;
    private String videotitle;
    private String videoauthor;
    private List<videocore> videocore;
    public Integer getCoursetype() {
        return coursetype;
    }
    public void setCoursetype(Integer coursetype) {
        this.coursetype = coursetype;
    }
    public String getVideoauthor() {
        return videoauthor;
    }
    public void setVideoauthor(String videoauthor) {
        this.videoauthor = videoauthor;
    }
    public List<videocore> getVideocore() {
        return videocore;
    }
    public void setVideocore(List<videocore> videocore) {
        this.videocore = videocore;
    }
    public BmobFile getVideofile() {
        return videofile;
    }
    public void setVideofile(BmobFile videofile) {
        this.videofile = videofile;
    }
    public String getVideotitle() {
        return videotitle;
    }
    public void setVideotitle(String videotitle) {
        this.videotitle = videotitle;
    }
    public Integer getChapterAct() {
        return chapterAct;
    }

    public void setChapterAct(Integer chapterAct) {
        this.chapterAct = chapterAct;
    }

    public Integer getChapter() {
        return chapter;
    }

    public void setChapter(Integer chapter) {
        this.chapter = chapter;
    }


}
