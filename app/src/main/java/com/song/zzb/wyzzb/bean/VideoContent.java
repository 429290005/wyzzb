package com.song.zzb.wyzzb.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by song on 2016/2/11.
 */
public class VideoContent extends BmobObject {
    private String core;
    private String coredsc;

    public BmobFile getPicfile() {
        return picfile;
    }

    public void setPicfile(BmobFile picfile) {
        this.picfile = picfile;
    }

    public String getCoredsc() {
        return coredsc;
    }

    public void setCoredsc(String coredsc) {
        this.coredsc = coredsc;
    }

    public String getCore() {
        return core;
    }

    public void setCore(String core) {
        this.core = core;
    }

    private BmobFile picfile;
}
