package cn.rongcloud.im.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import cn.rongcloud.im.R;
import cn.rongcloud.im.server.utils.NToast;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by kzhang3 on 2016/7/20.
 */
public class DetailsActivity extends BaseActivity {


    Button bt1 = null;
    TextView tv1 = null;
//    private SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        tv1 = (TextView) findViewById(R.id.targetUserid);
        bt1 = (Button) findViewById(R.id.enterChatting);

        bt1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String myuserId = sp.getString("userid","");
                String targetUserId = tv1.getText().toString();
                List<String> startDisList = new ArrayList<>();
                startDisList.add(targetUserId);

                RongIM.getInstance().createDiscussion("洽谈室", startDisList, new RongIMClient.CreateDiscussionCallback() {
                    @Override
                    public void onSuccess(String s) {
                        NToast.shortToast(DetailsActivity.this, "开启洽谈室");
                        RongIM.getInstance().startDiscussionChat(DetailsActivity.this, s, "洽谈室");
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });

            }
        });

    }
}
