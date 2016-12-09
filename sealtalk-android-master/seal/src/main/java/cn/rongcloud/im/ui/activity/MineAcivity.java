package cn.rongcloud.im.ui.activity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.rongcloud.im.R;

/**
 * Created by Ivan.Wang on 2016/11/13.
 */

public class MineAcivity extends BaseActivity {
    private GridView gview;
    private GridView gview2;
    private GridView gview3;
    private List<Map<String, Object>> imageNoTextList;
    private List<Map<String, Object>> serviceTextList;
    private List<Map<String, Object>> myActivityTextList;
    private SimpleAdapter icon_adapter;
    private SimpleAdapter service_adapter;
    private SimpleAdapter myActivity_adapter;

    private int[] icon = { R.drawable.address_book, R.drawable.calendar,
            R.drawable.camera};
    private String[] number = { "0", "0","0"};
    private String[] label = { "监控报警", "急需缴费", "工单"};

    private String[] service_name = { "已购服务", "服务订单","到期服务"};
    private String[] service_label = { "管理所以服务", "0个订单待付款", "0个服务快到期"};

    private int[] icon1 = { R.drawable.address_book, R.drawable.calendar};
    private String[] myActivity_name = { "我的收藏", "现在活动"};
    private String[] myActivity_label = { "点击查看收藏好文", "报名的活动进度"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_mine);
        init();
    }


    private void init() {
        gview = (GridView) findViewById(R.id.gview);
        gview2 = (GridView) findViewById(R.id.gview1);
        gview3 = (GridView) findViewById(R.id.gview2);
//        Addbutton = (Button) mView.findViewById(R.id.addbutton);
//        Addbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //MyDataActivity
//                //ElectricFragment.class
//                startActivity(new Intent(getActivity(), RouteStatusActivity.class));
//
//
//            }
//        });

//        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//
//                // Send intent to SingleViewActivity
//                //Intent i =
//                //      new Intent(getApplicationContext(), SingleViewActivity.class);
//                // Pass image index
//                //i.putExtra("id", position);
//                //startActivity(i);
//                Toast.makeText(this, "haha,Insert Employee Successfully!", Toast.LENGTH_LONG).show();
//            }
//        });
        imageNoTextList = new ArrayList<Map<String, Object>>();
        serviceTextList = new ArrayList<Map<String, Object>>();
        myActivityTextList = new ArrayList<Map<String, Object>>();
        fillData();
        fillServiceData();
        fillMyActivity();
        String [] from ={"image","number","text"};
        int [] image_number_text = {R.id.image,R.id.number,R.id.text};
        icon_adapter = new SimpleAdapter(this, imageNoTextList, R.layout.itemimage_number_label, from, image_number_text);
        service_adapter = new SimpleAdapter(this, serviceTextList, R.layout.itemimage_label_mine, from, image_number_text);
        myActivity_adapter = new SimpleAdapter(this,myActivityTextList,R.layout.itemimage_label_mine,from,image_number_text);

        gview.setAdapter(icon_adapter);
        gview2.setAdapter(service_adapter);
        gview3.setAdapter(myActivity_adapter);
    }

    public List<Map<String, Object>> fillData(){
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("number",number[i]);
            map.put("text", label[i]);
            imageNoTextList.add(map);
        }
        return imageNoTextList;
    }

    public List<Map<String, Object>> fillServiceData(){
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("number",service_name[i]);
            map.put("text", service_label[i]);
            serviceTextList.add(map);
        }
        return serviceTextList;
    }

    public List<Map<String, Object>> fillMyActivity(){
        for(int i=0;i<icon1.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon1[i]);
            map.put("number",myActivity_name[i]);
            map.put("text", myActivity_label[i]);
            myActivityTextList.add(map);
        }
        return myActivityTextList;
    }

//    public List<Map<String, Object>> getData1(){
//        for(int i=0;i<icon1.length;i++){
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("image", icon1[i]);
//            map.put("text", iconName1[i]);
//            serviceTextList.add(map);
//        }
//
//        return serviceTextList;
//    }



}
