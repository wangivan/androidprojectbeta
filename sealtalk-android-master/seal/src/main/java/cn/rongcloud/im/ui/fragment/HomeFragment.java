package cn.rongcloud.im.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.rongcloud.im.R;
import cn.rongcloud.im.ui.activity.MyDeviceListActivity;

/**
 * Created by Ivan.Wang on 2016/10/29.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {


    private GridView gview;
    private GridView gview2;
    private List<Map<String, Object>> data_list;
    private List<Map<String, Object>> data_list1;
    private SimpleAdapter sim_adapter;
    private SimpleAdapter sim_adapter1;
    private Button Addbutton;


    // ͼƬ��װΪһ������
//    private int[] icon1 = { R.drawable.address_book, R.drawable.calendar,
//            R.drawable.camera, R.drawable.clock, R.drawable.games_control,
//            R.drawable.messenger};
    private String[] icon1 = { "0", "0",
            "0%", "0", "0",
            "0%"};
    private String[] iconName1 = { "今日产量", "今日目标产量", "今日完成度", "今日能耗", "今日能耗量", "今日能耗占比"};

    private int[] icon = { R.drawable.address_book, R.drawable.calendar,
            R.drawable.camera, R.drawable.clock, R.drawable.games_control,
            R.drawable.messenger, R.drawable.ringtone, R.drawable.settings,
            R.drawable.speech_balloon, R.drawable.weather, R.drawable.world,
            R.drawable.youtube };
    private String[] iconName = { "OPC平台", "工厂管家", "数据分析", "生成圈表", "OPC平台", "工厂管家", "数据分析",
            "生成圈表", "OPC平台", "工厂管家", "数据分析", "生成圈表" };



    public static HomeFragment instance = null;

    public static HomeFragment getInstance() {
        if (instance == null) {
            instance = new HomeFragment();
        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home_fragment, null);
        init(view);
        return view;
    }


     private void init(View mView) {

        gview = (GridView) mView.findViewById(R.id.gview);
        gview2 = (GridView) mView.findViewById(R.id.gview1);
        Addbutton = (Button) mView.findViewById(R.id.addbutton);
        Addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MyDataActivity
                //ElectricFragment.class
                //RouteStatusActivity.class
                // MineAcivity.class
                // TabHoderActivity.class
                //MoreTextViewActivity.class
                startActivity(new Intent(getActivity(), MyDeviceListActivity.class));


            }
        });



         gview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // Send intent to SingleViewActivity
//                Intent i =
//                      new Intent(getContext(), ElectricFragment.class);
//                // Pass image index
//                i.putExtra("id", 1);
//                startActivity(i);
//                Intent two =
//                        new Intent(getContext(), RouteStatusActivity.class);
//                // Pass image index
//                i.putExtra("id", 2);
//                startActivity(two);
                Intent i =
                      new Intent(getContext(), MyDeviceListActivity.class);
                i.putExtra("id", 1);
                startActivity(i);
//                Toast.makeText(getActivity(), "haha,Insert Employee Successfully!", Toast.LENGTH_LONG).show();
            }
        });
        //�½�List
        data_list = new ArrayList<Map<String, Object>>();
        data_list1 = new ArrayList<Map<String, Object>>();
        //��ȡ����
        getData();
        getData1();
        //�½�������
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
         int [] toString={R.id.titletext,R.id.text};
        sim_adapter = new SimpleAdapter(this.getContext(), data_list1, R.layout.itemlabel, from, toString);
        sim_adapter1 = new SimpleAdapter(this.getContext(), data_list, R.layout.item, from, to);
        //����������
        gview.setAdapter(sim_adapter);
        gview2.setAdapter(sim_adapter1);
    }

    public List<Map<String, Object>> getData(){
        //cion��iconName�ĳ�������ͬ�ģ�������ѡ��һ������
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

        return data_list;
    }

    public List<Map<String, Object>> getData1(){
        //cion��iconName�ĳ�������ͬ�ģ�������ѡ��һ������
        for(int i=0;i<icon1.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon1[i]);
            map.put("text", iconName1[i]);
            data_list1.add(map);
        }

        return data_list1;
    }

    @Override
    public void onClick(View v) {

    }
}
