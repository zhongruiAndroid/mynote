package com.mynote.module.home.inter;

/**
 * Created by Administrator on 2016/10/10.
 */
public interface AddDataInter {
    boolean saveData();
    void clearData();
    interface AddDataFinish{
        void addDataFinish();
    }
}
