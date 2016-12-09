package cn.rongcloud.im.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.rongcloud.im.R;
import cn.rongcloud.im.server.network.async.AsyncTaskManager;
import cn.rongcloud.im.server.network.http.HttpException;
import cn.rongcloud.im.server.response.LineChartDataResponse;
import cn.rongcloud.im.server.response.PieChartDataResponse;
import cn.rongcloud.im.server.response.PieChartSignalWorkShopDataResponse;
import cn.rongcloud.im.server.utils.NToast;
import cn.rongcloud.im.server.widget.LoadDialog;
import cn.rongcloud.im.ui.adapter.ReportReadOnlyTableAdapter;
import cn.rongcloud.im.ui.adapter.ReportTableAdapter;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

import static java.util.Collections.sort;

/**
 * Created by ivan.wang on 2016/11/30.
 */

public class WorkShopChartActivity extends BaseActivity {

    private LineChartView lineChart;

    private PieChart mChart;

    private ListView lv;

    private ListView pieLv;

    private TextView lineTopText;

    private TextView pieTopText;

    private static final int  LINECHART= 10;

    private final static int PIECHARTDATA = 11;

    private String params ;

//    private LineChartView lineChart1;

    //    String[] electronicDate = {"5-23","5-22","6-22","5-23","5-22","2-22","5-22","4-22","9-22","10-22","11-22","12-22","1-22","6-22","5-23","5-22","2-22","5-22","4-22","9-22","10-22","11-22","12-22","4-22","9-22","10-22","11-22","zxc"};//X轴的标注
//    int[] score= {74,22,18,79,20,74,20,74,42,90,74,42,90,50,42,90,33,10,74,22,18,79,20,74,22,18,79,20};//图表的数据
    private List<String> electronicDate = new ArrayList<>();
    private List<Float> score = new ArrayList<>();
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private ArrayList<String> deviceList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_linechart_workshop);

        Intent intent = getIntent();
        params = intent.getStringExtra(ReportTableAdapter.KEY);

        TextView lineChartTitle = (TextView) findViewById(R.id.line_chart_title);
        lineChartTitle.setText(params + "耗电分时统计(电量)");
        lineTopText = (TextView) findViewById(R.id.time_solt);
        lineTopText.setText(params + "耗电分时统计");
        pieTopText = (TextView) findViewById(R.id.pie_time_solt);
        pieTopText.setText(params + "设备耗电统计");
        lineChart = (LineChartView)findViewById(R.id.line_chart);
        lv = (ListView) findViewById(R.id.lv);
        mChart = (PieChart) findViewById(R.id.spread_pie_chart);
        showChart(mChart);
        pieLv = (ListView) findViewById(R.id.pie_lv);

        request(LINECHART);
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
                Intent intent = new Intent(this,DayReportSearchCheckBoxinListViewActivity.class);
                intent.putStringArrayListExtra("DEVICELIST",deviceList);
                intent.putExtra("LABEL",params);
                intent.putExtra("TYPE","电量");
                startActivity(intent);
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

    private void updatePieChartlistview(PieChartSignalWorkShopDataResponse dataResponse) {
        ReportReadOnlyTableAdapter adapter = new ReportReadOnlyTableAdapter(this, getPieChartTableData(dataResponse));
        pieLv.setAdapter(adapter);
        setListViewHeightBasedOnChildren(pieLv);
    }

    public List<Map<String, Object>> getPieChartTableData(PieChartSignalWorkShopDataResponse dataResponse) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < dataResponse.getResult().size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("time", dataResponse.getResult().get(i).getCode());
            map.put("quantity", dataResponse.getResult().get(i).getData().toString());
            list.add(map);
        }
        return list;
    }

    private void updateLineChartlistview(LineChartDataResponse dataResponse) {
        ReportReadOnlyTableAdapter adapter = new ReportReadOnlyTableAdapter(this, getLineChartTableData(dataResponse));
        lv.setAdapter(adapter);
        setListViewHeightBasedOnChildren(lv);
    }

    public List<Map<String, Object>> getLineChartTableData(LineChartDataResponse dataResponse) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < dataResponse.getResult().size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("time", dataResponse.getResult().get(i).getTime());
            map.put("quantity", dataResponse.getResult().get(i).getData().toString());
            list.add(map);
        }
        return list;
    }

    /**
     * 初始化LineChart的一些设置
     */
    private void initLineChart(){
        Line line = new Line(mPointValues).setColor(Color.parseColor("#0000FF"));  //折线的颜色
        List<Line> lines = new ArrayList<Line>();
//        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
        line.setCubic(true);//曲线是否平滑
        line.setStrokeWidth(1);//线条的粗细，默认是3
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//		line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X轴下面坐标轴字体是斜的显示还是直的，true是斜的显示
//	    axisX.setTextColor(Color.WHITE);  //设置字体颜色
//        D6D6D9
        axisX.setTextColor(Color.parseColor("#000000"));//

//	    axisX.setName("未来几天的天气");  //表格名称
        axisX.setTextSize(11);//设置字体大小
        axisX.setMaxLabelChars(7); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
//	    data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线



        Axis axisY = new Axis();  //Y轴
        axisY.setName("出流量(Kbps)");//y轴标注
        axisY.setTextSize(11);//设置字体大小
//        axisY.setTextColor()
        axisY.setTextColor(Color.parseColor("#000000"));//
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边
        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);  //缩放类型，水平
        lineChart.setMaxZoom((float) 3);//缩放比例
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);

        axisY.setName("单位(x10000)");//y轴标注
        data.setAxisYLeft(axisY);
//        lineChart1.setInteractive(true);
//        lineChart1.setZoomType(ZoomType.HORIZONTAL);  //缩放类型，水平
//        lineChart1.setMaxZoom((float) 3);//缩放比例
//        lineChart1.setLineChartData(data);
//        lineChart1.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 尼玛搞的老子好辛苦！！！见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         * 下面几句可以设置X轴数据的显示个数（x轴0-7个数据），当数据点个数小于（29）的时候，缩小到极致hellochart默认的是所有显示。当数据点个数大于（29）的时候，
         * 若不设置axisX.setMaxLabelChars(int count)这句话,则会自动适配X轴所能显示的尽量合适的数据个数。
         * 若设置axisX.setMaxLabelChars(int count)这句话,
         * 33个数据点测试，若 axisX.setMaxLabelChars(10);里面的10大于v.right= 7; 里面的7，则
         刚开始X轴显示7条数据，然后缩放的时候X轴的个数会保证大于7小于10
         若小于v.right= 7;中的7,反正我感觉是这两句都好像失效了的样子 - -!
         * 并且Y轴是根据数据的大小自动设置Y轴上限
         * 若这儿不设置 v.right= 7; 这句话，则图表刚开始就会尽可能的显示所有数据，交互性太差
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right= 7;
        lineChart.setCurrentViewport(v);

//        Viewport v1 = new Viewport(lineChart1.getMaximumViewport());
//        v1.left = 0;
//        v1.right= 7;
//        lineChart1.setCurrentViewport(v1);
    }

    /**
     * X 轴的显示
     */
    private void getAxisXLables(){
        mAxisXValues.clear();
        for (int i = 0; i < electronicDate.size(); i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(electronicDate.get(i)));
        }
    }
    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints(){
        mPointValues.clear();
        for (int i = 0; i < score.size(); i++) {
            mPointValues.add(new PointValue(i, score.get(i)));
        }
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        switch (requestCode) {
            case LINECHART:
                return action.getLineChartData(params);
            case PIECHARTDATA:
                return action.getPieChartData(params);
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        if (result != null) {
            switch (requestCode) {
                case LINECHART:
                    final LineChartDataResponse lcdr = (LineChartDataResponse) result;
                    Collections.sort(lcdr.getResult());
                    electronicDate.clear();
                    score.clear();
                    for (LineChartDataResponse.ResultEntity entity : lcdr.getResult()) {
                        electronicDate.add(entity.getTime());
                        score.add(Float.valueOf(entity.getData())/10000);
                    }

                    getAxisXLables();//获取x轴的标注
                    getAxisPoints();//获取坐标点
                    initLineChart();//初始化
                    updateLineChartlistview(lcdr);
//                    updatelistview();
                    request(PIECHARTDATA);
                    break;
                case PIECHARTDATA:
                    final PieChartSignalWorkShopDataResponse pcdr = (PieChartSignalWorkShopDataResponse) result;
                    PieData mPieData = getPieData(pcdr);
                    mChart.setData(mPieData);
                    mChart.refreshDrawableState();
                    updatePieChartlistview(pcdr);
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
            case LINECHART:
                LoadDialog.dismiss(mContext);
                NToast.shortToast(mContext, "折现数据获取不到");
                break;
            case PIECHARTDATA:
                LoadDialog.dismiss(mContext);
                NToast.shortToast(mContext, "饼图数据获取不到");
                break;
        }
    }


    private void showChart(PieChart pieChart) {
        pieChart.setHoleRadius(60f);  //半径
        pieChart.setTransparentCircleRadius(64f); // 半透明圈
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.setRotationAngle(90); // 初始旋转角度
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比

        pieChart.setCenterText("");  //饼状图中间的文字
//        pieChart.setData(getPieData());
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
    private PieData getPieData(PieChartSignalWorkShopDataResponse response) {
        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();  //yVals用来表示封装每个饼块的实际数据
        for (PieChartSignalWorkShopDataResponse.ResultEntity entity : response.getResult()) {
            yValues.add(new PieEntry(Float.valueOf(entity.getData()),entity.getCode()));
            deviceList.add(entity.getCode());
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

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


}
