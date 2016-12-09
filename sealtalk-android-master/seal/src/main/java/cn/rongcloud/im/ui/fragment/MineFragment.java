package cn.rongcloud.im.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.rongcloud.im.App;
import cn.rongcloud.im.R;
import cn.rongcloud.im.SealConst;
import cn.rongcloud.im.server.SealAction;
import cn.rongcloud.im.server.broadcast.BroadcastManager;
import cn.rongcloud.im.server.network.async.AsyncTaskManager;
import cn.rongcloud.im.server.network.async.OnDataListener;
import cn.rongcloud.im.server.network.http.HttpException;
import cn.rongcloud.im.server.response.VersionResponse;
import cn.rongcloud.im.server.utils.RongGenerate;
import cn.rongcloud.im.server.widget.SelectableRoundedImageView;
import cn.rongcloud.im.ui.activity.AboutRongCloudActivity;
import cn.rongcloud.im.ui.activity.AccountSettingActivity;
import cn.rongcloud.im.ui.activity.MyAccountActivity;
import cn.rongcloud.im.ui.activity.PublishActivity;
import cn.rongcloud.im.ui.activity.PublishMainActivity;
import io.rong.imkit.RongIM;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    private static final int COMPAREVERSION = 54;
    public static final String SHOWRED = "SHOWRED";
    public static MineFragment instance = null;

    public static MineFragment getInstance() {
        if (instance == null) {
            instance = new MineFragment();
        }
        return instance;
    }

    private View mView;

    private SharedPreferences sp;

    private SelectableRoundedImageView imageView;

    private TextView mName;

    private LinearLayout mUserProfile, mMineStting, mMineService, mMineAbout,mPublish;

    private ImageView mNewVersionView;

    private boolean isHasNewVersion;

    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.seal_mine_fragment, null);
        initViews(mView);
        initData();
        BroadcastManager.getInstance(getActivity()).addAction(SealConst.CHANGEINFO, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String userid = sp.getString("loginid", "");
                String username = sp.getString("loginnickname", "");
                String userportrait = sp.getString("loginPortrait", "");
                mName.setText(username);
                ImageLoader.getInstance().displayImage(TextUtils.isEmpty(userportrait) ? RongGenerate.generateDefaultAvatar(username, userid) : userportrait, imageView, App.getOptions());
            }
        });
        compareVersion();
        return mView;
    }

    private void compareVersion() {
        AsyncTaskManager.getInstance(getActivity()).request(COMPAREVERSION, new OnDataListener() {
            @Override
            public Object doInBackground(int requsetCode, String parameter) throws HttpException {
                return new SealAction(getActivity()).getSealTalkVersion(SealConst.GETVERSION);
            }

            @Override
            public void onSuccess(int requestCode, Object result) {
                if (result != null) {
                    VersionResponse response = (VersionResponse) result;
                    if (response != null) {

                        String[] s = response.getAndroid().getVersion().split("\\.");
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < s.length; i++) {
                            sb.append(s[i]);
                        }

                        String[] s2 = SealConst.SEALTALKVERSION.split("\\.");
                        StringBuilder sb2 = new StringBuilder();
                        for (int i = 0; i < s2.length; i++) {
                            sb2.append(s2[i]);
                        }
                        if (Integer.parseInt(sb.toString()) > Integer.parseInt(sb2.toString())) {
                            mNewVersionView.setVisibility(View.VISIBLE);
                            url = response.getAndroid().getUrl();
                            isHasNewVersion = true;
                            BroadcastManager.getInstance(getActivity()).sendBroadcast(SHOWRED);
                        }
                    }
                }
            }

            @Override
            public void onFailure(int requestCode, int state, Object result) {

            }
        });
    }

    private void initData() {
        sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        String userid = sp.getString("loginid", "");
        String username = sp.getString("loginnickname", "");
        String userportrait = sp.getString("loginPortrait", "");
        mName.setText(username);
        ImageLoader.getInstance().displayImage(TextUtils.isEmpty(userportrait) ? RongGenerate.generateDefaultAvatar(username, userid) : userportrait, imageView, App.getOptions());
    }

    private void initViews(View mView) {
        mNewVersionView = (ImageView) mView.findViewById(R.id.new_version_icon);
        imageView = (SelectableRoundedImageView) mView.findViewById(R.id.mine_header);
        mName = (TextView) mView.findViewById(R.id.mine_name);
        mUserProfile = (LinearLayout) mView.findViewById(R.id.start_user_profile);
        mMineStting = (LinearLayout) mView.findViewById(R.id.mine_setting);
        mMineService = (LinearLayout) mView.findViewById(R.id.mine_service);
        mMineAbout = (LinearLayout) mView.findViewById(R.id.mine_about);
        mPublish = (LinearLayout)mView.findViewById(R.id.mine_publish);
        mUserProfile.setOnClickListener(this);
        mMineStting.setOnClickListener(this);
        mMineService.setOnClickListener(this);
        mMineAbout.setOnClickListener(this);
        mPublish.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_user_profile:
                startActivity(new Intent(getActivity(), MyAccountActivity.class));
                break;
            case R.id.mine_setting:
                startActivity(new Intent(getActivity(), AccountSettingActivity.class));
                break;
            case R.id.mine_publish:
                startActivity(new Intent(getActivity(), PublishActivity.class));
                break;
            case R.id.mine_service:
                // KEFU146001495753714 正式  KEFU145930951497220 测试
                RongIM.getInstance().startCustomerServiceChat(getActivity(), "KEFU146001495753714", "在线客服", null);
                break;
            case R.id.mine_about:
                mNewVersionView.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), AboutRongCloudActivity.class);
                intent.putExtra("isHasNewVersion", isHasNewVersion);
                if (!TextUtils.isEmpty(url)) {
                    intent.putExtra("url", url);
                }
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BroadcastManager.getInstance(getActivity()).destroy(SealConst.CHANGEINFO);
    }
}
