package cn.rongcloud.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.rongcloud.im.App;
import cn.rongcloud.im.R;
import cn.rongcloud.im.db.DBManager;
import cn.rongcloud.im.server.SealAction;
import cn.rongcloud.im.server.network.async.AsyncTaskManager;
import cn.rongcloud.im.server.network.http.HttpException;
import cn.rongcloud.im.server.pinyin.Friend;
import cn.rongcloud.im.server.response.DeleteFriendResponse;
import cn.rongcloud.im.server.response.LoginResponse;
import cn.rongcloud.im.server.utils.NToast;
import cn.rongcloud.im.server.utils.RongGenerate;
//VoIP start 1
import cn.rongcloud.im.server.widget.LoadDialog;
import cn.rongcloud.im.ui.fragment.ContactsFragment;
import io.rong.calllib.RongCallClient;
import io.rong.calllib.RongCallSession;
import io.rong.imkit.RongCallAction;
import io.rong.imkit.RongVoIPIntent;
//VoIP end 1
import io.rong.imlib.model.Conversation;
import io.rong.imkit.RongIM;

/**
 * Created by AMing on 16/7/12.
 * Company RongCloud
 */
public class SingleContactActivity extends BaseActivity {

    private TextView mContactName;

    private ImageView mContactHeader;

    private Friend friend;

    private static final int DELETEFRIENDS = 5;
    Button launchChatting;
    Button VoipChatting;
    Button videoChatting;
    Button deleteFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_contact);
        launchChatting = (Button) findViewById(R.id.launchChat);
        VoipChatting = (Button) findViewById(R.id.VOIPchatting);
        videoChatting = (Button)findViewById(R.id.videochatting);
        deleteFriend = (Button)findViewById(R.id.deletefriend);
        getSupportActionBar().hide();
        mContactName = (TextView) findViewById(R.id.contact_name);
        mContactHeader = (ImageView) findViewById(R.id.contact_header);
        friend = (Friend) getIntent().getSerializableExtra("FriendDetails");
        if (friend != null) {
            mContactName.setText(friend.getName());
            ImageLoader.getInstance().displayImage(TextUtils.isEmpty(friend.getPortraitUri()) ? RongGenerate.generateDefaultAvatar(friend.getName(), friend.getUserId()) : friend.getPortraitUri(), mContactHeader, App.getOptions());
        }
    }

    public void startChat(View view) {
        RongIM.getInstance().startPrivateChat(mContext, friend.getUserId(), friend.getName());
        finish();
    }

    //VoIP start 2
    public void startVoice(View view) {
        RongCallSession profile = RongCallClient.getInstance().getCallSession();
        if (profile != null && profile.getActiveTime() > 0) {
            Toast.makeText(mContext, getString(io.rong.imkit.R.string.rc_voip_call_start_fail), Toast.LENGTH_SHORT).show();
            return;
        }
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            Toast.makeText(mContext, getString(io.rong.imkit.R.string.rc_voip_call_network_error), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(RongVoIPIntent.RONG_INTENT_ACTION_VOIP_SINGLEAUDIO);
        intent.putExtra("conversationType", Conversation.ConversationType.PRIVATE.getName().toLowerCase());
        intent.putExtra("targetId", friend.getUserId());
        intent.putExtra("callAction", RongCallAction.ACTION_OUTGOING_CALL.getName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage(getPackageName());
        getApplicationContext().startActivity(intent);
    }

    public void startVideo(View view) {
        RongCallSession profile = RongCallClient.getInstance().getCallSession();
        if (profile != null && profile.getActiveTime() > 0) {
            Toast.makeText(mContext, getString(io.rong.imkit.R.string.rc_voip_call_start_fail), Toast.LENGTH_SHORT).show();
            return;
        }
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            Toast.makeText(mContext, getString(io.rong.imkit.R.string.rc_voip_call_network_error), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(RongVoIPIntent.RONG_INTENT_ACTION_VOIP_SINGLEVIDEO);
        intent.putExtra("conversationType", Conversation.ConversationType.PRIVATE.getName().toLowerCase());
        intent.putExtra("targetId", friend.getUserId());
        intent.putExtra("callAction", RongCallAction.ACTION_OUTGOING_CALL.getName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage(getPackageName());
        getApplicationContext().startActivity(intent);
    }

    public void deleteFriend(View view) {
        request(DELETEFRIENDS);
    }
    //VoIP end 2

    public void finishPage(View view) {
        this.finish();
    }


    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        switch (requestCode) {
            case DELETEFRIENDS:
                String userId= mContext.getSharedPreferences("config", Context.MODE_PRIVATE).getString("loginid", "");
                return action.deleteFriend(userId,friend.getUserId());
           }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        if (result != null) {
            switch (requestCode) {
                case DELETEFRIENDS:
                    final DeleteFriendResponse lrres = (DeleteFriendResponse) result;
                    if(lrres.getCode()==200){
                        DBManager.getInstance(mContext).getDaoSession().getFriendDao().delete(new cn.rongcloud.im.db.Friend(friend.getUserId(),
                                friend.getName(),
                                friend.getPortraitUri(),
                                friend.getDisplayName(),
                                String.valueOf(friend.getStatus()),
                                null));
                        Toast.makeText(mContext, "好友已删除", Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(SingleContactActivity.this,MainActivity.class);
                        //startActivity(intent);
                        deleteFriend.setEnabled(false);
                        deleteFriend.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        VoipChatting.setEnabled(false);
                        VoipChatting.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        videoChatting.setEnabled(false);
                        videoChatting.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        launchChatting.setEnabled(false);
                        launchChatting.setBackgroundColor(Color.parseColor("#D3D3D3"));

                    }
            }
        }
    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        if (state == AsyncTaskManager.HTTP_NULL_CODE || state == AsyncTaskManager.HTTP_ERROR_CODE) {
            LoadDialog.dismiss(mContext);
            NToast.shortToast(mContext, R.string.network_not_available);
            return;
        }
        switch (requestCode) {
            case DELETEFRIENDS:
                LoadDialog.dismiss(mContext);
                Toast.makeText(mContext, "删除好友出错，请联系administrator", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
