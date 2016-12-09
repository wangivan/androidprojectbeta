package cn.rongcloud.im.message.provider;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import cn.rongcloud.im.R;
import cn.rongcloud.im.ui.activity.CallActivity;
import io.rong.imkit.RongContext;
import io.rong.imkit.widget.provider.InputProvider;

/**
 * Created by kzhang3 on 2016/8/4.
 */
public class CallProvider extends InputProvider.ExtendProvider {

    private Context mContext;

    public CallProvider(RongContext context) {
        super(context);
        mContext = context;
    }

    @Override
    public Drawable obtainPluginDrawable(Context context) {
        return context.getResources().getDrawable(R.drawable.icon_1);
    }

    @Override
    public CharSequence obtainPluginTitle(Context context) {
        return "打电话";
    }

    @Override
    public void onPluginClick(View view) {

        //NToast.shortToast(mContext, "请检查网络");
        startCall(view.getContext());


    }


    private void startCall(Context context) {

        Intent intent = new Intent(((FragmentActivity) context), CallActivity.class);
        //intent.putExtra("conversationType", getCurrentConversation().getConversationType().getValue());
        intent.putExtra("callnumber", "13761923861");
        startActivityForResult(intent, 123);
    }
}
