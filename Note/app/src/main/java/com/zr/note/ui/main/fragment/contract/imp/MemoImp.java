package com.zr.note.ui.main.fragment.contract.imp;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zr.note.R;
import com.zr.note.adapter.CommonAdapter;
import com.zr.note.adapter.ViewHolder;
import com.zr.note.base.IPresenter;
import com.zr.note.database.DBManager;
import com.zr.note.tools.MyDialog;
import com.zr.note.tools.StringUtils;
import com.zr.note.ui.main.entity.MemoBean;
import com.zr.note.ui.main.fragment.contract.MemoCon;

import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 */
public class MemoImp extends IPresenter<MemoCon.View> implements MemoCon.Presenter{
    private List<MemoBean>memoBeanList;
    public MemoImp(Context context) {
        super(context);
    }

    @Override
    public List<MemoBean> selectData(ListView lv_memo_list, boolean isOrderByCreateTime) {
        memoBeanList= DBManager.getInstance(mContext).selectMemo(isOrderByCreateTime);
        CommonAdapter<MemoBean> commonAdapter = new CommonAdapter<MemoBean>(mContext, memoBeanList, R.layout.item_memo) {
            @Override
            public void convert(ViewHolder helper, MemoBean item) {
                helper.setText(R.id.tv_data_id, StringUtils.getStringLength(getCount(), helper.getPosition()) + "" + (helper.getPosition() + 1))
                        .setText(R.id.tv_memo_content, item.getDataContent());
                TextView tv_reminder = helper.getTextView(R.id.tv_reminder);
                if (item.getDataRemark() == null || item.getDataRemark().trim().length() == 0) {
                    tv_reminder.setVisibility(View.GONE);
                } else {
                    tv_reminder.setVisibility(View.VISIBLE);
                    tv_reminder.setText(item.getDataRemark());
                }

            }
        };
        lv_memo_list.setAdapter(commonAdapter);
        return memoBeanList;
    }

    @Override
    public MemoBean copyMemo(int position) {
        return memoBeanList.get(position);
    }

    @Override
    public void deleteMemoById(MyDialog.Builder mDialog,final int id) {
        mDialog=new MyDialog.Builder(mContext);
        mDialog.setMessage(getStr(R.string.delete_data));
        mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = DBManager.getInstance(mContext).deleteMemo(id);
                if(flag){
                    mView.showMsg("删除成功");
                    mView.selectData();
                }else{
                    mView.showMsg("删除失败");
                }
                dialog.dismiss();
            }
        });
        mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mDialog.create().show();
    }

    @Override
    public void deleteMemoById(MyDialog.Builder mDialog,final String[] id) {
        mDialog=new MyDialog.Builder(mContext);
        mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = DBManager.getInstance(mContext).deleteAccount(id);
                if(flag){
                    mView.showMsg("删除成功");
                    mView.selectData();
                }else{
                    mView.showMsg("删除失败");
                }
                dialog.dismiss();
            }
        });
        mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mDialog.create().show();
    }
}
