package cn.rongcloud.im.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import cn.rongcloud.im.R;

/**
 * Created by Ivan.Wang on 2016/10/19.
 */

public class TestActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_regitster);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
       }
}
