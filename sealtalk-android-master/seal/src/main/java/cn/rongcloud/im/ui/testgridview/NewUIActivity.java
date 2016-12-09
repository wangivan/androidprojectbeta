package cn.rongcloud.im.ui.testgridview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import cn.rongcloud.im.R;
import cn.rongcloud.im.ui.activity.MyDataActivity;

public class NewUIActivity extends Activity {
	private GridView gview;
	private GridView gview2;
	private List<Map<String, Object>> data_list;
	private List<Map<String, Object>> data_list1;
	private SimpleAdapter sim_adapter;
	private SimpleAdapter sim_adapter1;
	private Button Addbutton;


	// ͼƬ��װΪһ������
	private int[] icon1 = { R.drawable.address_book, R.drawable.calendar,
			R.drawable.camera, R.drawable.clock, R.drawable.games_control,
			R.drawable.messenger};
	private String[] iconName1 = { "通讯录", "日历", "照相机", "时钟", "游戏", "短信"};

	private int[] icon = { R.drawable.address_book, R.drawable.calendar,
			R.drawable.camera, R.drawable.clock, R.drawable.games_control,
			R.drawable.messenger, R.drawable.ringtone, R.drawable.settings,
			R.drawable.speech_balloon, R.drawable.weather, R.drawable.world,
			R.drawable.youtube };
	private String[] iconName = { "通讯录", "日历", "照相机", "时钟", "游戏", "短信", "铃声",
			"设置", "语音", "天气", "浏览器", "视频" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		gview = (GridView) findViewById(R.id.gview);
		gview2 = (GridView) findViewById(R.id.gview1);
		Addbutton = (Button) findViewById(R.id.addbutton);
		Addbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(NewUIActivity.this,"Hlello",Toast.LENGTH_LONG).show();
				startActivity(new Intent(NewUIActivity.this, MyDataActivity.class));
			}
		});

		gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
									int position, long id) {

//				 Send intent to SingleViewActivity
//				Intent i =
//				      new Intent(getApplicationContext(), SingleViewActivity.class);
//				 Pass image index
//				i.putExtra("id", position);
//				startActivity(i);
				Toast.makeText(NewUIActivity.this, "haha,Insert Employee Successfully!", Toast.LENGTH_LONG).show();
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
		sim_adapter = new SimpleAdapter(this, data_list, R.layout.item, from, to);
		sim_adapter1 = new SimpleAdapter(this, data_list, R.layout.item, from, to);
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
			map.put("image", icon[i]);
			map.put("text", iconName1[i]);
			data_list1.add(map);
		}

		return data_list1;
	}
	

}
