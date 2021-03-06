package com.zr.note.ui.main.fragment.contract.imp;

import android.content.Context;

import com.zr.note.base.IPresenter;
import com.zr.note.database.DBManager;
import com.zr.note.ui.main.entity.JokeBean;
import com.zr.note.ui.main.fragment.contract.AddJokeCon;

/**
 * Created by Administrator on 2016/10/24.
 */
public class AddJokeImp extends IPresenter<AddJokeCon.View> implements AddJokeCon.Presenter {
    public AddJokeImp(Context context) {
        super(context);
    }
    @Override
    public boolean addJoke(JokeBean bean) {
        if(DBManager.getNewInstance(mContext).addOrEditJoke(bean)>0){
            if(bean.get_id()==-1){
                mView.showMsg("保存成功");
            }else{
                mView.showMsg("修改成功");
            }
            return true;
        }else{
            if(bean.get_id()==-1){
                mView.showMsg("保存失败");
            }else{
                mView.showMsg("修改失败");
            }
            return false;
        }
    }
}
