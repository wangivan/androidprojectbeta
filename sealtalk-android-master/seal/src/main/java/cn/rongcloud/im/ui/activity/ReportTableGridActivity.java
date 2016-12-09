package cn.rongcloud.im.ui.activity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.rongcloud.im.R;
import cn.rongcloud.im.ui.adapter.ReportTableAdapter;

/**
 * Created by Ivan.Wang on 2016/11/27.
 */

public class ReportTableGridActivity extends BaseActivity {
    ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_table);
        lv = (ListView) findViewById(R.id.lv);
        updatelistview();
    }

    // 更新listview
    public void updatelistview() {


//        String id = cr.getColumnName(0);// 获取第1列
//        String job = cr.getColumnName(2);// 获取第3列
//        String address = cr.getColumnName(4);// 获取第5列
//        String student = cr.getColumnName(5);// 获取第6列
//        String id = ""
//        String[] ColumnNames = { id, job, address, student };

//        ListAdapter adapter = new MySimpleCursorAdapter(this,
//                R.layout.listviewlayout, cr, ColumnNames, new int[] { R.id.id,
//                R.id.job, R.id.addr, R.id.student });
        // layout为listView的布局文件，包括三个TextView，用来显示三个列名所对应的值
        // ColumnNames为数据库的表的列名
        // 最后一个参数是int[]类型的，为view类型的id，用来显示ColumnNames列名所对应的值。view的类型为TextView
        ReportTableAdapter adapter = new ReportTableAdapter(this, getData());
        lv.setAdapter(adapter);

    }

    public List<Map<String, Object>> getData(/*List<DataEntity> data_list*/) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 3; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("time", "00:00-00:30");
            map.put("quantity", "888888");
            list.add(map);
        }
        return list;
    }
}
