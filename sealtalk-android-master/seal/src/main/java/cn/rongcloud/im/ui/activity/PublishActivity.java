package cn.rongcloud.im.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import cn.rongcloud.im.R;


/**
 * Created by ivan.wang on 2016/7/27.
 */
public class PublishActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout PublishNews , PublishMy ,PulishMoreProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("发布");
        setContentView(R.layout.sr_publish_activity);
        initView();
    }

    private void initView() {
        PublishNews = (LinearLayout) findViewById(R.id.publish_news);
        PublishMy = (LinearLayout) findViewById(R.id.publish_mine);
        PulishMoreProject = (LinearLayout) findViewById(R.id.publish_more_projects);
        PublishMy.setOnClickListener(this);
        PublishNews.setOnClickListener(this);
        PulishMoreProject.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.publish_news:
                startActivity(new Intent(mContext, PublishMainActivity.class));
                break;
            case R.id.publish_mine:
                startActivity(new Intent(mContext, DisplayActivity.class));
                break;
            case R.id.publish_more_projects:
                startActivity(new Intent(mContext, DisplayActivity.class));
                break;
        }
    }
}
