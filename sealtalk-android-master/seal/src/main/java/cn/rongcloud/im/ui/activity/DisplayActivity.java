package cn.rongcloud.im.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;





import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.rongcloud.im.R;
import cn.rongcloud.im.model.DataEntity;
import cn.rongcloud.im.server.network.http.HttpException;
import cn.rongcloud.im.server.response.SearchPublishResponse;
import cn.rongcloud.im.server.utils.NToast;
import cn.rongcloud.im.server.widget.LoadDialog;
import cn.rongcloud.im.ui.adapter.MyAdapter;

/**
 * Created by kzhang3 on 2016/7/20.
 */
public class DisplayActivity extends BaseActivity {

    private static final int DETAIL_CODE = 2110;

    private SharedPreferences sp;

    private ListView listView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        listView=(ListView)findViewById(R.id.list);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        request(DETAIL_CODE);
//        List<Map<String, Object>> list=getData();
//        listView.setAdapter(new MyAdapter(this, list));
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.main, menu);
        System.out.print("1231312");
        return true;
    }

    public List<Map<String, Object>> getData(List<DataEntity> dataList){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (DataEntity entity : dataList) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("image", R.drawable.ic_launcher);
            map.put("title", entity.getTitle());
            map.put("status1", entity.getClickFeq());
            list.add(map);
        }

//        for (int i = 0; i < dataList.size(); i++) {
//            Map<String, Object> map=new HashMap<String, Object>();
//            map.put("image", R.drawable.ic_launcher);
//            map.put("title", "高科技数码团队期待加入您"+i);
//            map.put("status1", "审批中"+i);
//            list.add(map);
//        }
        return list;
    }

    @Override
    public Object doInBackground(int requsetCode,String id) throws HttpException {
        switch (requsetCode) {
            case DETAIL_CODE:
                String username =  sp.getString("username","");
                return action.searchDetails(1,username);
        }
        return super.doInBackground(requsetCode,id);
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        switch (requestCode) {
            case DETAIL_CODE:
                SearchPublishResponse res = (SearchPublishResponse) result;
                if (res.getCode() == 110) {
                    LoadDialog.dismiss(mContext);
                    NToast.shortToast(mContext, "已更新发布");
                    List<Map<String, Object>> list=getData(res.getDataList());
                    listView.setAdapter(new MyAdapter(this, list));
                }
        }

    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        switch (requestCode) {
            case DETAIL_CODE:
                NToast.shortToast(mContext, "查找公告栏失败");
                LoadDialog.dismiss(mContext);
                break;
        }

    }

}
