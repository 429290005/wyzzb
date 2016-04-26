package com.song.zzb.wyzzb.bean;


import java.io.Serializable;

/**
 * Created by song on 2016/2/12.
 */
public class videocore implements Serializable {
    private static final long serialVersionUID = 1L;
    private String core;
    private String coretitle;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCorecontent() {
        return corecontent;
    }

    public void setCorecontent(String corecontent) {
        this.corecontent = corecontent;
    }

    public String getCoretitle() {
        return coretitle;
    }

    public void setCoretitle(String coretitle) {
        this.coretitle = coretitle;
    }

    public String getCore() {
        return core;
    }

    public void setCore(String core) {
        this.core = core;
    }

    private String corecontent;
    private String url;
}
