package cn.rongcloud.im.ui.activity;

import android.os.Bundle;
import android.widget.ListView;

import cn.rongcloud.im.R;
import cn.rongcloud.im.ui.widget.PieChart;

/**
 * Created by ivan.wang on 2016/11/29.
 */

public class PieChartCustomActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custmize_piechart);
        PieChart mChart = (PieChart) findViewById(R.id.pie_chart);
        String[] titles = new String[] {"钱包余额","金钱袋资产","金宝箱资产"};
        mChart.setTitles(titles);
        int[] colors = new int[]{0xfff5a002,0xfffb5a2f,0xff36bc99};
        mChart.setColors(colors);
        mChart.setValues(new double[]{999,999,999});
        mChart.postInvalidate();

    }
}
