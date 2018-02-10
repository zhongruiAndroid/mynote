package com.mynote.module.account.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.mynote.R;
import com.mynote.base.MyAdapter;
import com.mynote.module.account.bean.AccountBean;

/**
 * Created by Administrator on 2018/2/2.
 */

public class AccountAdapter extends MyAdapter<AccountBean> {
    private String searchInfo;
    private boolean isEdit;
    public AccountAdapter(Context mContext, int layoutId, int pageSize) {
        super(mContext, layoutId, pageSize);
    }

    public AccountAdapter(Context mContext, int layoutId, int pageSize, NestedScrollView nestedScrollView) {
        super(mContext, layoutId, pageSize, nestedScrollView);
    }

    @Override
    public void bindData(LoadMoreViewHolder holder, int pos, AccountBean item) {
        int countLength = String.valueOf(getItemCount()-1).length();
        int position=pos+1;
        StringBuffer stringBuffer=new StringBuffer();
        for (int i = 0; i < countLength-String.valueOf(position).length(); i++) {
            stringBuffer.append("0");
        }
        String dataAccountHTML=item.getDataAccount();
        String dataSourceHTML=item.getDataSource();
        if(searchInfo!=null){//关键字搜索变色
            dataAccountHTML= getSearchColorString(item.getDataAccount());
            dataSourceHTML=  getSearchColorString(item.getDataSource());
        }

        final CheckBox cb_check = (CheckBox) holder.getView(R.id.cb_check);
        if(isEdit){
            cb_check.setVisibility(View.VISIBLE);
            cb_check.setChecked(item.isCheck());
            cb_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG+"===","==="+cb_check.isChecked());
                    item.setCheck(cb_check.isChecked());
                }
            });
        }else{
            cb_check.setVisibility(View.GONE);
        }

        holder.setText(R.id.tv_data_id, stringBuffer.toString() + "" + position);

        TextView tv_source = holder.getTextView(R.id.tv_source);
        TextView tv_account = holder.getTextView(R.id.tv_account);

        tv_source.setText(Html.fromHtml(dataSourceHTML==null?"":dataSourceHTML));
        tv_account.setText(Html.fromHtml(dataAccountHTML));

    }
    public void setSearchInfo(String info) {
        searchInfo=info;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public boolean isEdit() {
        return isEdit;
    }

    @NonNull
    private String getSearchColorString(String dataContentHTML) {
        if(TextUtils.isEmpty(dataContentHTML)){
            return dataContentHTML;
        }
        StringBuffer dataContent=new StringBuffer(dataContentHTML);
        int indexOf = dataContentHTML.toLowerCase().indexOf(searchInfo.toLowerCase());
        if(indexOf>=0){
            String sameInfo= "<font color='#18B4ED'>"+dataContent.subSequence(indexOf, indexOf + searchInfo.length())+"</font>";
            dataContent=dataContent.replace(indexOf, indexOf + searchInfo.length(), sameInfo);

        }
        dataContentHTML=  dataContent.toString();
        return dataContentHTML;
    }

}
