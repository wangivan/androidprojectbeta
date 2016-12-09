package cn.rongcloud.im.ui.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import cn.rongcloud.im.R;
import cn.rongcloud.im.server.network.async.AsyncTaskManager;
import cn.rongcloud.im.server.network.http.HttpException;
import cn.rongcloud.im.server.response.DayReportResponse;
import cn.rongcloud.im.server.utils.NToast;
import cn.rongcloud.im.server.widget.LoadDialog;
import cn.rongcloud.im.ui.widget.hvsrollview.HScrollView;
import cn.rongcloud.im.ui.widget.hvsrollview.OnScrollChangedListener;
import cn.rongcloud.im.ui.widget.hvsrollview.TextItem;
import android.widget.LinearLayout.LayoutParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static cn.rongcloud.im.utils.DateTimePickDialogUtil.spliteString;

/**
 * Created by Ivan.Wang on 2016/12/5.
 */

public class DetailDeviceMonthReportActivity extends BaseActivity implements OnScrollChangedListener {

    private GestureDetector mGestureDetector = null;
    private HScrollView mItemRoom = null;
    private ScrollView mVertical = null;
    private HScrollView mTimeItem = null;
    private LinearLayout mContain = null;
    private final static int SEARCH=10;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGestureDetector = new GestureDetector(mGestureListener);
//        makeItems(mapItem);
//        setContentView(mContain);


        request(SEARCH);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.more_report, menu);
//        return true;
//    }

    private void makeItems(Map<String, Map<String, String>> mapItem){
        mItemRoom = new HScrollView(this, mGestureDetector);
        mItemRoom.setListener(this);
        LinearLayout VLayout = new LinearLayout(this);
        VLayout.setOrientation(LinearLayout.VERTICAL);
        Set<String> hcolumnnames = new LinkedHashSet<>();
            Iterator<Map.Entry<String, Map<String, String>>> entryIterator = mapItem.entrySet().iterator();
            while(entryIterator.hasNext()) {
                LinearLayout HLayout = new LinearLayout(this);
                Map.Entry<String, Map<String, String>> entry = entryIterator.next();
                Iterator<Map.Entry<String,String>> iterator = entry.getValue().entrySet().iterator();
                while(iterator.hasNext()){
                    Map.Entry<String, String> textItemEntry = iterator.next();
                    hcolumnnames.add(textItemEntry.getKey());
                    TextItem item = new TextItem(this);
                    item.setText(textItemEntry.getValue());
                    item.setBackgroundColor(Color.WHITE);
                    LayoutParams params = new LayoutParams(400,100);
                    HLayout.addView(item, params);
                }
                VLayout.addView(HLayout);
            }

//        for(int i = 0;i < 4;i++){
//            LinearLayout HLayout = new LinearLayout(this);
//            for(int j = 0;j < 4;j++){
//                TextItem item = new TextItem(this);
//                item.setText(i*10+j + "vh");
//                item.setBackgroundColor(Color.WHITE);
//                LayoutParams params = new LayoutParams(400,100);
//                HLayout.addView(item, params);
//            }
//            VLayout.addView(HLayout);
//        }

        mItemRoom.addView(VLayout);

        mVertical = new ScrollView(this);
        LinearLayout hlayout = new LinearLayout(this);
        LinearLayout vlayout = new LinearLayout(this);
        vlayout.setOrientation(LinearLayout.VERTICAL);
        Iterator<String> viter = mapItem.keySet().iterator();
        for(;viter.hasNext();){
            TextItem item = new TextItem(this);
            item.setBackgroundColor(Color.WHITE);
            item.setText(viter.next());
            LinearLayout.LayoutParams params = new LayoutParams(400,100);
            vlayout.addView(item, params);
        }

//        for(int i = 0;i < 4;i++){
//            TextItem item = new TextItem(this);
//            item.setBackgroundColor(Color.WHITE);
//            item.setText("第"+i+"工厂");
//            LinearLayout.LayoutParams params = new LayoutParams(400,100);
//            vlayout.addView(item, params);
//        }
        hlayout.addView(vlayout);
        hlayout.addView(mItemRoom);

        mVertical.addView(hlayout);

        LinearLayout tophlayout = new LinearLayout(this);
        mTimeItem = new HScrollView(this, mGestureDetector);
        mTimeItem.setListener(this);
        LinearLayout layout = new LinearLayout(this);
        for(String text : hcolumnnames){
            TextItem item = new TextItem(this);
            item.setText(text);
            item.setBackgroundColor(Color.WHITE);
            LinearLayout.LayoutParams params = new LayoutParams(400,100);
            layout.addView(item,params);
        }
//        for(int i = 0;i < 4;i++){
//            TextItem item = new TextItem(this);
//            item.setText("厂房数据");
//            item.setBackgroundColor(Color.WHITE);
//            LinearLayout.LayoutParams params = new LayoutParams(400,100);
//            layout.addView(item,params);
//        }
        mTimeItem.addView(layout);
        TextItem item = new TextItem(this);
        item.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams params = new LayoutParams(400,100);
        tophlayout.addView(item,params);
        tophlayout.addView(mTimeItem);

        mContain = new LinearLayout(this);
        mContain.setOrientation(LinearLayout.VERTICAL);
        mContain.addView(tophlayout);
        mContain.addView(mVertical);
    }


    private GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            if(Math.abs(distanceX) > Math.abs(distanceY)) {
                return true;
            }
            return false;
        }

    };
    @Override
    public void onScrollChanged(HScrollView scroll, int x, int y, int oldx,
                                int oldy) {
        if(scroll == mItemRoom){
            mTimeItem.scrollTo(x, y);
        }else if(scroll == mTimeItem){
            mItemRoom.scrollTo(x, y);
        }

    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        switch (requestCode) {
            case SEARCH:
                Intent intent = getIntent();
                String type = intent.getStringExtra("TYPE");
                ArrayList<String> list = intent.getStringArrayListExtra("DEVICELIST");
                String time =  intent.getStringExtra("TIME");
                String yearStr = spliteString(time, "年", "index", "front"); // 年份
                String monthAndDay = spliteString(time, "年", "index", "back"); // 月日

                String monthStr = spliteString(monthAndDay, "月", "index", "front"); // 月
                String dayStr = spliteString(monthAndDay, "月", "index", "back"); // 日
                time = yearStr + "-" + monthStr + "-" + dayStr;
                String workshop = intent.getStringExtra("WORKSHOP");
                return action.getDayReportData(workshop,type,list,time);
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        if (result != null) {
            switch (requestCode) {
                case SEARCH:
                    DayReportResponse response = (DayReportResponse)result;
                    if (response.getCode() != 200)
                        return;
                    Map<String,Map<String,String>> mapItem = new LinkedHashMap<>();
                    for (DayReportResponse.ResultEntity entity : response.getResult()) {
                        Map<String,String> item = new LinkedHashMap<>();
                        Iterator<Map.Entry<String, Float>> iter = entity.getData().entrySet().iterator();
                        for (;iter.hasNext();) {
                            Map.Entry<String, Float> entry = iter.next();
                            item.put(entry.getKey(),entry.getValue().toString());
                        }
                        item.put("summation",entity.getSummation());
                        mapItem.put(entity.getCode(),item);
                    }
                    makeItems(mapItem);
                    setContentView(mContain);

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
            case SEARCH:
                LoadDialog.dismiss(mContext);
                NToast.shortToast(mContext, "查询数据获取不到");
                break;
        }
    }



}
