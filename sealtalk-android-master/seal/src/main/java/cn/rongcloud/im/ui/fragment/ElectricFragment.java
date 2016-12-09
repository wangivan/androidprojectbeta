package cn.rongcloud.im.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.rongcloud.im.R;
import cn.rongcloud.im.server.SealAction;
import cn.rongcloud.im.server.network.async.AsyncTaskManager;
import cn.rongcloud.im.server.network.async.OnDataListener;
import cn.rongcloud.im.server.network.http.HttpException;
import cn.rongcloud.im.server.response.LineChartDataResponse;
import cn.rongcloud.im.server.response.PieChartDataResponse;
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

/**
 * Created by Ivan.Wang on 2016/11/7.
 */

public class ElectricFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,OnDataListener {

    private AsyncTaskManager atm = AsyncTaskManager.getInstance(getActivity());

    private LineChartView lineChart;

    private PieChart mChart;

    private ListView lv;
    private ListView pieLv;

    private SwipeRefreshLayout mSwipeLayout;

    private static final int  LINECHART= 10;

    private final static int PIECHARTDATA = 11;

    private static final int REFRESH_COMPLETE = 0X110;

    private List<String> electronicDate = new ArrayList<>();
    private List<Float> score = new ArrayList<>();
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
    //    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // TODO Auto-generated method stub
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.more_report, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_linechart_simple,null);//注意不要指定父视图
        lineChart = (LineChartView)view.findViewById(R.id.line_chart);
        lv = (ListView) view.findViewById(R.id.lv);
        mChart = (PieChart) view.findViewById(R.id.spread_pie_chart);
        showChart(mChart);
        pieLv = (ListView) view.findViewById(R.id.pie_lv);
        TextView tv = (TextView)view.findViewById(R.id.pie_time_solt);
        tv.setText("车间耗电统计");
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swipe_ly);
        mSwipeLayout.setOnRefreshListener(this);
        initListener();
        atm.request(LINECHART,this);

        return view;
    }

    private void initListener() {
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if(lv != null && lv.getChildCount() > 0){
                    // check if the first item of the list is visible
                    boolean firstItemVisible = lv.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = lv.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                mSwipeLayout.setEnabled(enable);
            }});
    }


    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case REFRESH_COMPLETE:
                    atm.request(LINECHART,ElectricFragment.this);
                    mSwipeLayout.setRefreshing(false);
                    break;

            }
        };
    };

//    private void updatelistview() {
//        ReportTableAdapter adapter = new ReportTableAdapter(this, getData());
//        lv.setAdapter(adapter);
//        pieLv.setAdapter(adapter);
//    }

//    public List<Map<String, Object>> getData(/*List<DataEntity> data_list*/) {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        for (int i = 0; i < electronicDate.size(); i++) {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("time", electronicDate.get(i));
//            map.put("quantity", score.get(i).toString());
//            list.add(map);
//        }
//        return list;
//    }
    private void updatePieChartlistview(PieChartDataResponse dataResponse) {
        ReportTableAdapter adapter = new ReportTableAdapter(getContext(), getPieChartTableData(dataResponse));
        pieLv.setAdapter(adapter);
        setListViewHeightBasedOnChildren(pieLv);
    }

    public List<Map<String, Object>> getPieChartTableData(PieChartDataResponse dataResponse) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < dataResponse.getResult().size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("time", dataResponse.getResult().get(i).getZtName());
            map.put("quantity", dataResponse.getResult().get(i).getData().toString());
            list.add(map);
        }
        return list;
    }

    private void updateLineChartlistview(LineChartDataResponse dataResponse) {
        ReportReadOnlyTableAdapter adapter = new ReportReadOnlyTableAdapter(getContext(), getLineChartTableData(dataResponse));
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

//    public List<Map<String, Object>> getData(/*List<DataEntity> data_list*/) {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        for (int i = 0; i < 3; i++) {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("time", "00:00");
//            map.put("quantity", "8888");
//            list.add(map);
//        }
//        return list;
//    }

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
                return new SealAction(getActivity()).getLineChartData();
            case PIECHARTDATA:
                return new SealAction(getActivity()).getPieChartData();
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
                    atm.request(PIECHARTDATA,this);
                    break;
                case PIECHARTDATA:
                    final PieChartDataResponse pcdr = (PieChartDataResponse) result;
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
            LoadDialog.dismiss(getContext());
            NToast.shortToast(getContext(), R.string.network_not_available);
            return;
        }
        switch (requestCode) {
            case LINECHART:
                LoadDialog.dismiss(getContext());
                NToast.shortToast(getContext(), "折现数据获取不到");
                break;
            case PIECHARTDATA:
                LoadDialog.dismiss(getContext());
                NToast.shortToast(getContext(), "饼图数据获取不到");
                break;
        }
    }

    @Override
    public void onRefresh() {

        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }

//    private long exitTime = 0;
//    @Override
//    public void onBackPressed() {
//        if(System.currentTimeMillis() - exitTime > 2000) {
//            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//            exitTime = System.currentTimeMillis();
//        } else {
//            finish();
//            System.exit(0);
//            android.os.Process.killProcess(android.os.Process.myPid());
//        }
//    }

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
    private PieData getPieData(PieChartDataResponse response) {
        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();  //yVals用来表示封装每个饼块的实际数据
        for (PieChartDataResponse.ResultEntity entity : response.getResult()) {
            yValues.add(new PieEntry(Float.valueOf(entity.getData()),entity.getZtName()));
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
//        pieDataSet.setValueLinePart1OffsetPercentage(20f);
//        pieDataSet.setValueLinePart1Length(0.1f);
//        pieDataSet.setValueLinePart2Length(0.2f);
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);


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
