package cn.rongcloud.im.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.rongcloud.im.R;
import cn.rongcloud.im.model.DataEntity;
import cn.rongcloud.im.ui.adapter.DeviceListAdapter;

/**
 * Created by Ivan.Wang on 2016/11/17.
 */

public class Tab2 extends Fragment {


    private ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tab2,null);//注意不要指定父视图
        listView=(ListView)view.findViewById(R.id.device_list);
        initData();
        return view;
    }

    private void initData() {
        listView.setAdapter(new DeviceListAdapter(getContext(),getData()));
    }

    public List<Map<String, Object>> getData(/*List<DataEntity> data_list*/) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 3; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", R.drawable.searchbox3);
            map.put("title", "ZTE3");
            map.put("status1", "在线 11/09 00:00");
            list.add(map);
        }
        return list;
    }


}
