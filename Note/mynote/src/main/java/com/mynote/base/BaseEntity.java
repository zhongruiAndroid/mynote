package com.mynote.base;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/13.
 */
public class BaseEntity implements Serializable {
    private int _id;
    private String uid;
    private long updateTime;
    private long creatTime;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(long creatTime) {
        this.creatTime = creatTime;
    }
}
