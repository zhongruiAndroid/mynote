package com.zr.note.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zr.note.R;
import com.zr.note.base.BaseActivity;
import com.zr.note.base.customview.MyRadioButton;
import com.zr.note.inter.MyOnClickListener;
import com.zr.note.tools.PhoneUtils;
import com.zr.note.ui.main.activity.contract.MainContract;
import com.zr.note.ui.main.activity.contract.imp.MainImp;
import com.zr.note.ui.main.constant.IntentParam;
import com.zr.note.ui.main.constant.RequestCode;
import com.zr.note.ui.main.fragment.AccountFragment;
import com.zr.note.ui.main.fragment.JokeFragment;
import com.zr.note.ui.main.fragment.MemoFragment;
import com.zr.note.ui.main.fragment.SpendFragment;
import com.zr.note.view.MyPopupwindow;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainContract.View, MainContract.Presenter> implements MainContract.View {
    @BindView(R.id.ctl_layout)
    CollapsingToolbarLayout ctl_layout;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rb_main_account)
    MyRadioButton rb_main_account;
    @BindView(R.id.rb_main_memo)
    MyRadioButton rb_main_memo;
    @BindView(R.id.rb_main_joke)
    MyRadioButton rb_main_joke;
    @BindView(R.id.rb_main_spend)
    MyRadioButton rb_main_spend;
    @BindView(R.id.rg_main)
    RadioGroup rg_main;
    @BindView(R.id.view_backgroud)
    View view_backgroud;
    private long exitTime;
    private AccountFragment accountFragment;
    private MemoFragment memoFragment;
    private JokeFragment jokeFragment;
    private SpendFragment spendFragment;
    private int tabIndex=0;
    @Override
    protected int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        getToolbar().setNavigationOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                drawerlayout.openDrawer(Gravity.LEFT);
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        rb_main_account.setChecked(true);
        ctl_layout.setExpandedTitleColor(getResources().getColor(R.color.transparent));
        ctl_layout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));


        accountFragment=AccountFragment.newInstance();
        addFragment(R.id.fl_fragment, accountFragment);
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_main_account:
                        tabIndex=0;
                        showFragment(accountFragment);
                        hideFragment(memoFragment);
                        hideFragment(jokeFragment);
                        hideFragment(spendFragment);
                        break;
                    case R.id.rb_main_memo:
                        tabIndex=1;
                        if (memoFragment == null) {
                            memoFragment = MemoFragment.newInstance();
                            hideFragment(accountFragment);
                            addFragment(R.id.fl_fragment, memoFragment);
                            hideFragment(jokeFragment);
                            hideFragment(spendFragment);
                        } else {
                            hideFragment(accountFragment);
                            showFragment(memoFragment);
                            hideFragment(jokeFragment);
                            hideFragment(spendFragment);
                        }
                        break;
                    case R.id.rb_main_joke:
                        tabIndex=2;
                        if (jokeFragment == null) {
                            jokeFragment = JokeFragment.newInstance();
                            hideFragment(accountFragment);
                            hideFragment(memoFragment);
                            addFragment(R.id.fl_fragment, jokeFragment);
                            hideFragment(spendFragment);
                        } else {
                            hideFragment(accountFragment);
                            hideFragment(memoFragment);
                            showFragment(jokeFragment);
                            hideFragment(spendFragment);
                        }
                        break;
                    case R.id.rb_main_spend:
                        tabIndex=3;
                        if (spendFragment == null) {
                            spendFragment = SpendFragment.newInstance();
                            hideFragment(accountFragment);
                            hideFragment(memoFragment);
                            hideFragment(jokeFragment);
                            addFragment(R.id.fl_fragment, spendFragment);
                        } else {
                            hideFragment(accountFragment);
                            hideFragment(memoFragment);
                            hideFragment(jokeFragment);
                            showFragment(spendFragment);
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void setToolbarStyle() {
        setToolbarTitle("Note");
        setNavigationIcon(R.drawable.drawer_menu);
        getToolbar().setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    @Override
    protected int setOptionsMenu() {
        return R.menu.menu_main;
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void viewOnClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
//                STActivity(AddDataActivity.class);
                mIntent.putExtra(IntentParam.tabIndex,tabIndex);
                STActivityForResult(mIntent,AddDataActivity.class, RequestCode.addDataRequestCode);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode){
            case RequestCode.addDataRequestCode:
                //是否添加或者修改数据回传到主页，更新数据
                boolean addDataIsSuccess = data.getBooleanExtra(IntentParam.addDataCode, false);
                int addDataIndex = data.getIntExtra(IntentParam.addDataIndex, 0);
                if(addDataIsSuccess){
                    selectFragmentData(addDataIndex,true);
                }
                break;
        }
    }

    @Override
    protected void menuOnClick(int itemId) {
        switch (itemId) {
            case R.id.action_settings:
                showSeting();
                break;
            case R.id.tv_orderBy_create:
                selectFragmentData(tabIndex,true);
                break;
            case R.id.tv_orderBy_update:
                selectFragmentData(tabIndex,false);
                break;
            case R.id.tv_batchDelete:
                break;
        }
    }


    private void selectFragmentData(int addDataIndex,boolean isOrderByCreate) {
        switch (addDataIndex){
            case 0:
                accountFragment.selectData(isOrderByCreate);
            break;
            case 1:
                if(memoFragment!=null){
                    memoFragment.selectData(isOrderByCreate);
                }

            break;
            case 2:
                if(memoFragment!=null){
                    memoFragment.selectData(isOrderByCreate);
                }
            break;
            case 3:
                if(memoFragment!=null){
                    memoFragment.selectData(isOrderByCreate);
                }
            break;
        }
    }

    private void showSeting() {
//        tv_orderBy_create
        View view = inflateView(R.layout.popu_options, null);
        TextView tv_orderBy_create = (TextView) view.findViewById(R.id.tv_orderBy_create);
        TextView tv_orderBy_update = (TextView) view.findViewById(R.id.tv_orderBy_update);
        TextView tv_batchDelete = (TextView) view.findViewById(R.id.tv_batchDelete);
        tv_orderBy_create.setOnClickListener(this);
        tv_orderBy_update.setOnClickListener(this);
        tv_batchDelete.setOnClickListener(this);
        MyPopupwindow popupwindow = new MyPopupwindow(this,view);
        int xoff = PhoneUtils.getPhoneWidth(this) - PhoneUtils.dip2px(this, 125);
        view_backgroud.setVisibility(View.VISIBLE);
        popupwindow.showAsDropDown(getToolbar(), xoff, 0);
        popupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                view_backgroud.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected MainImp initPresenter() {
        return new MainImp(this);
    }


    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 1500) {
            showToastS("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
