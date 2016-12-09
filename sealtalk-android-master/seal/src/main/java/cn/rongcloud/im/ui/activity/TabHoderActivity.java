package cn.rongcloud.im.ui.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;

import cn.rongcloud.im.R;
import cn.rongcloud.im.ui.fragment.ElectricFragment;

/**
 * Created by Ivan.Wang on 2016/11/15.
 */
// instead of by MyDeviceListActivity
public class TabHoderActivity extends ActivityGroup {

    private TabHost tabHost;//声明TabHost组件的对象
    private Intent mAIntent;
    private Intent electronicIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabhost_mian_layout);

        tabHost=(TabHost)findViewById(android.R.id.tabhost);//获取tabHost对象
        tabHost.setup();//初始化TabHost组件



        // 此处是我解决异常加的一行代码,如果继承Activity的话可将此行注释
        tabHost.setup(this.getLocalActivityManager());


        LayoutInflater inflater=LayoutInflater.from(this);//声明并实例化一个LayoutInflater对象
        //关于LayoutInflater详细，请看我的另外一篇转载的总结
//        inflater.inflate(R.layout.activity_new_regitster, tabHost.getTabContentView());
//        inflater.inflate(R.layout.tab2, tabHost.getTabContentView());
//        inflater.inflate(R.layout.tab3, tabHost.getTabContentView());
        this.mAIntent = new Intent(this,TestActivity.class);
        this.electronicIntent = new Intent(this,ElectricFragment.class);
        tabHost.addTab(tabHost.newTabSpec("tab01")
                .setIndicator("电量")
                .setContent(this.electronicIntent));//添加第一个标签页
        tabHost.addTab(tabHost.newTabSpec("tab02")
                .setIndicator("产量")
                .setContent(this.mAIntent));//添加第二个标签页
//        tabHost.addTab(tabHost.newTabSpec("tab03")
//                .setIndicator("标签页三")
//                .setContent(this.mAIntent));//添加第三个标签页
    }
}
