package com.zr.note.ui.main.fragment.contract;

import android.widget.ListView;

import com.zr.note.base.BasePresenter;
import com.zr.note.base.BaseView;
import com.zr.note.ui.main.entity.AccountBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 */
public interface AccountCon {
    interface View extends BaseView{

    }
    interface Presenter extends BasePresenter<View>{
       List<AccountBean> selectData(ListView lv_account_list);
    }
}
