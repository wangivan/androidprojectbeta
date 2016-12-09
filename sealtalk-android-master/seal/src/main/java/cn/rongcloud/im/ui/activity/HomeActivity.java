package cn.rongcloud.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.rongcloud.im.R;
import cn.rongcloud.im.ui.fragment.HomeFragment;

/**
 * Created by Ivan.Wang on 2016/12/1.
 */

public class HomeActivity extends BaseActivity {

//    private GridView gview;
    private GridView gview2;
    private List<Map<String, Object>> data_list;
//    private List<Map<String, Object>> data_list1;
//    private SimpleAdapter sim_adapter;
    private SimpleAdapter sim_adapter1;
//    private Button Addbutton;

//    private String[] icon1 = { "0", "0",
//            "0%", "0", "0",
//            "0%"};
//    private String[] iconName1 = { "今日产量", "今日目标产量", "今日完成度", "今日能耗", "今日能耗量", "今日能耗占比"};

//    private int[] icon = { R.drawable.address_book, R.drawable.calendar,
//            R.drawable.camera, R.drawable.clock, R.drawable.games_control,
//            R.drawable.messenger, R.drawable.ringtone, R.drawable.settings,
//            R.drawable.speech_balloon, R.drawable.weather, R.drawable.world,
//            R.drawable.youtube };
//    private String[] iconName = { "OPC平台", "工厂管家", "数据分析", "生成圈表", "OPC平台", "工厂管家", "数据分析",
//            "生成圈表", "OPC平台", "工厂管家", "数据分析", "生成圈表" };

    private int[] icon = { R.drawable.address_book,R.drawable.address_book};
    private String[] iconName = { "报表","月报"};

    public static HomeFragment instance = null;

    public static HomeFragment getInstance() {
        if (instance == null) {
            instance = new HomeFragment();
        }
        return instance;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.test);

        init();
    }


    private void init() {
        String userId= mContext.getSharedPreferences("config", Context.MODE_PRIVATE).getString("loginnickname", "");
        TextView user_title = (TextView)findViewById(R.id.user_id);
        user_title.setText(userId);
//        gview = (GridView) findViewById(R.id.gview);
        gview2 = (GridView) findViewById(R.id.gview1);
//        Addbutton = (Button) findViewById(R.id.addbutton);
//        Addbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //MyDataActivity
//                //ElectricFragment.class
//                //RouteStatusActivity.class
//                // MineAcivity.class
//                // TabHoderActivity.class
//                //MoreTextViewActivity.class
//                startActivity(new Intent(HomeActivity.this, MyDeviceListActivity.class));
//            }
//        });



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
                if (id == 1){
                    startActivity(new Intent(HomeActivity.this, DetailDeviceMonthReportActivity.class));
                    return;
                }
                Intent i = new Intent(HomeActivity.this, MyDeviceListActivity.class);
                startActivity(i);


//                Toast.makeText(getActivity(), "haha,Insert Employee Successfully!", Toast.LENGTH_LONG).show();
            }
        });
        //�½�List
        data_list = new ArrayList<Map<String, Object>>();
//        data_list1 = new ArrayList<Map<String, Object>>();
        //��ȡ����
        getData();
//        getData1();
        //�½�������
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        int [] toString={R.id.titletext,R.id.text};
//        sim_adapter = new SimpleAdapter(this, data_list1, R.layout.itemlabel, from, toString);
        sim_adapter1 = new SimpleAdapter(this, data_list, R.layout.item, from, to);
        //����������
//        gview.setAdapter(sim_adapter);
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

//    public List<Map<String, Object>> getData1(){
//        //cion��iconName�ĳ�������ͬ�ģ�������ѡ��һ������
//        for(int i=0;i<icon1.length;i++){
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("image", icon1[i]);
//            map.put("text", iconName1[i]);
//            data_list1.add(map);
//        }
//
//        return data_list1;
//    }

        private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
