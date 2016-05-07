package com.song.zzb.wyzzb.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by song on 2016/2/22.
 */
public class TiKuList extends BmobObject {
    public String getKuTitle() {
        return kuTitle;
    }

    public Integer getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(Integer questiontype) {
        this.questiontype = questiontype;
    }

    public  Integer questiontype;
    public void setKuTitle(String kuTitle) {
        this.kuTitle = kuTitle;
    }

    private String  kuTitle ;
    private  String difficulty;

    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
    }

    private  Integer orderby;
    public Integer getPerNumber() {
        return perNumber;
    }

    public void setPerNumber(Integer perNumber) {
        this.perNumber = perNumber;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }





    private  Integer perNumber;

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    private  Integer point;

}
