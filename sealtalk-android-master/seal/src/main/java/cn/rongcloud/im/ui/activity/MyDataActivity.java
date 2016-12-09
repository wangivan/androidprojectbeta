package cn.rongcloud.im.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import cn.rongcloud.im.R;

/**
 * Created by Ivan.Wang on 2016/11/7.
 */

public class MyDataActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data);
//        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("我的数据");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.de_actionbar_back);
//        actionBar.hide();
    }
}
