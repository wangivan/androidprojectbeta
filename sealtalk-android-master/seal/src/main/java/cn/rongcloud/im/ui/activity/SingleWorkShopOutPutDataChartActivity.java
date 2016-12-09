package cn.rongcloud.im.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.rongcloud.im.R;
import cn.rongcloud.im.server.network.async.AsyncTaskManager;
import cn.rongcloud.im.server.network.http.HttpException;
import cn.rongcloud.im.server.response.PieChartDataResponse;
import cn.rongcloud.im.server.response.PieChartSingleWorkShopOutPutDataResponse;
import cn.rongcloud.im.server.utils.NToast;
import cn.rongcloud.im.server.widget.LoadDialog;
import cn.rongcloud.im.ui.adapter.OutPutReportLinkedTableAdapter;
import cn.rongcloud.im.ui.adapter.ReportReadOnlyTableAdapter;
import cn.rongcloud.im.ui.adapter.ReportTableAdapter;

/**
 * Created by ivan.wang on 2016/11/30.
 */

public class SingleWorkShopOutPutDataChartActivity extends BaseActivity {

    private final static int PIECHARTDATA = 10;

    private ListView lv;

    private PieChart mChart;

    private TextView tv;

    private PieData mPieData = new PieData();

    private String params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        Intent intent = getIntent();
        params = intent.getStringExtra(OutPutReportLinkedTableAdapter.KEY);

        tv = (TextView) findViewById(R.id.time_solt);
        tv.setText(params + "设备产量统计");
        mChart = (PieChart) findViewById(R.id.spread_pie_chart);
        showChart(mChart);
        lv = (ListView) findViewById(R.id.lv);
        request(PIECHARTDATA);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.more_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (String.valueOf(item.getTitle())) {
            case "日报":
                startActivity(new Intent(this,DayReportSearchCheckBoxinListViewActivity.class));
                break;
            case "月报":
                Toast.makeText(this,"This is month ",Toast.LENGTH_SHORT).show();
                break;
            case "年报":
                Toast.makeText(this,"This is month ",Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showChart(PieChart pieChart) {
        //pieChart.setHoleColorTransparent(true);


        pieChart.setHoleRadius(60f);  //半径
        pieChart.setTransparentCircleRadius(64f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆

        //pieChart.setDescription("测试饼状图");

        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText("");  //饼状图中间的文字

        //设置数据
//        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(2f);
        l.setYEntrySpace(2f);
        l.setYOffset(0f);
        String [] lables= l.getLabels();

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }

    /**
     *
     */
    private PieData getPieData(PieChartSingleWorkShopOutPutDataResponse response) {

//        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容

//        for (int i = 0; i < count; i++) {
//            xValues.add("Quarterly" + (i + 1));  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
//        }

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
        /**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         */
//        float sum = 0;
//        for (PieChartDataResponse.ResultEntity entity : response.getResult()) {
//             sum += Float.valueOf(entity.getData());
//        }
//
        for (PieChartSingleWorkShopOutPutDataResponse.ResultEntity entity : response.getResult()) {
            yValues.add(new PieEntry(Float.valueOf(entity.getData()),entity.getMachcode()));
        }


//        float quarterly1 = 14;
//        float quarterly2 = 14;
//        float quarterly3 = 34;
//        float quarterly4 = 38;
//
//        yValues.add(new PieEntry(quarterly1,"quarterly1"));
//        yValues.add(new PieEntry(quarterly2, "quarterly2"));
//        yValues.add(new PieEntry(quarterly3, "quarterly3"));
//        yValues.add(new PieEntry(quarterly4, "quarterly4"));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, ""/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());

        // 饼图颜色
//        colors.add(Color.rgb(205, 205, 205));
//        colors.add(Color.rgb(114, 188, 223));
//        colors.add(Color.rgb(255, 123, 124));
//        colors.add(Color.rgb(57, 135, 200));
//
//        colors.add(Color.rgb(57, 135, 200));
//        colors.add(Color.rgb(57, 135, 200));
//        colors.add(Color.rgb(57, 135, 200));
//        colors.add(Color.rgb(57, 135, 200));
//        colors.add(Color.rgb(57, 135, 200));

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度
        //pieDataSet.setDrawValues(false);

        PieData pieData = new PieData(pieDataSet);
        //PieData pieData1 = new PieData();
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.WHITE);
        // data.setValueTypeface(mTfLight);

        return pieData;
    }






    // 更新listview
    public void updatelistview(PieChartSingleWorkShopOutPutDataResponse response) {
        ReportReadOnlyTableAdapter adapter = new ReportReadOnlyTableAdapter(this, getData(response));
        lv.setAdapter(adapter);
    }

    public List<Map<String, Object>> getData(PieChartSingleWorkShopOutPutDataResponse response/*List<DataEntity> data_list*/) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (PieChartSingleWorkShopOutPutDataResponse.ResultEntity entity : response.getResult()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("time", entity.getMachcode());
            map.put("quantity", entity.getData().toString());
            list.add(map);
        }
        return list;
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        switch (requestCode) {
            case PIECHARTDATA:
                return action.getPieChartSingleWorkShopOutPutData(params);
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        if (result != null) {
            switch (requestCode) {
                case PIECHARTDATA:
                    final PieChartSingleWorkShopOutPutDataResponse lcdr = (PieChartSingleWorkShopOutPutDataResponse) result;
                    PieData mPieData = getPieData(lcdr);
                    mChart.setData(mPieData);
                    mChart.refreshDrawableState();
                    updatelistview(lcdr);
                    break;
            }
        }
    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        if (state == AsyncTaskManager.HTTP_NULL_CODE || state == AsyncTaskManager.HTTP_ERROR_CODE) {
            LoadDialog.dismiss(mContext);
            NToast.shortToast(mContext, R.string.network_not_available);
            return;
        }
        switch (requestCode) {
            case PIECHARTDATA:
                LoadDialog.dismiss(mContext);
                NToast.shortToast(mContext, "饼图数据获取不到");
                break;
        }
    }

}
