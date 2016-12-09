package cn.rongcloud.im.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import cn.rongcloud.im.R;
import cn.rongcloud.im.server.network.http.HttpException;
import cn.rongcloud.im.server.response.PublishRequirementResponse;
import cn.rongcloud.im.server.utils.NToast;
import cn.rongcloud.im.server.widget.LoadDialog;




//import android.support.v7.app.AppCompatActivity;

public class PublishMainActivity extends BaseActivity implements View.OnClickListener{

    private static final int PULISH_CODE = 2106;

    private SharedPreferences sp;

    private ListView listView=null;
    public EditText ed1 = null;
    public EditText ed2 = null;
    public EditText ed3 = null;
    public EditText ed4 = null;
    public Button bt1 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publisher);
        //listView=(ListView)findViewById(R.id.list);
        //List<Map<String, Object>> list=getData();
        //listView.setAdapter(new MyAdapter(this, list));

       ed1 = (EditText) findViewById(R.id.brifTitle );
        ed2 = (EditText) findViewById(R.id.descrition);
        ed3 = (EditText) findViewById(R.id.CCode);
        ed4 = (EditText) findViewById(R.id.special);
        bt1 = (Button) findViewById(R.id.pushInfo);


        bt1.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                request(PULISH_CODE);
                Intent intent = new Intent();
//                intent.setClass(MainActivity.this,DisplayActivity.class);
                intent.setClass(PublishMainActivity.this,PublishActivity.class);
                startActivity(intent);
            }
        });

        sp = getSharedPreferences("config", MODE_PRIVATE);
    }


    @Override
    public Object doInBackground(int requsetCode, String id) throws HttpException {
        switch (requsetCode) {
            case PULISH_CODE:
               String username =  sp.getString("username","");
                String title = ed1.getText().toString();
                String description = ed2.getText().toString();
                String certificateNo = ed3.getText().toString();
                String feature = ed4.getText().toString();
                return action.publish(username,title,description,certificateNo,feature);
        }
        return super.doInBackground(requsetCode,id);
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        switch (requestCode) {
            case PULISH_CODE:
                PublishRequirementResponse res = (PublishRequirementResponse) result;
                if (res.getCode() == 106) {
                    LoadDialog.dismiss(mContext);
                    NToast.shortToast(mContext, res.getMessage());
                }
        }

    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        switch (requestCode) {
            case PULISH_CODE:
                NToast.shortToast(mContext, "发布失败");
                LoadDialog.dismiss(mContext);
                break;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pushInfo:
                request(PULISH_CODE);

        }
    }
}
