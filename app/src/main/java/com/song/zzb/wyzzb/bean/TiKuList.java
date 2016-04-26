package com.song.zzb.wyzzb.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by song on 2016/2/22.
 */
public class TiKuList extends BmobObject {
    public String getKuTitle() {
        return kuTitle;
    }

    public void setKuTitle(String kuTitle) {
        this.kuTitle = kuTitle;
    }

    private String  kuTitle ;
    private  String difficulty;

    public String getPerNumber() {
        return perNumber;
    }

    public void setPerNumber(String perNumber) {
        this.perNumber = perNumber;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }





    private  String perNumber;

}
