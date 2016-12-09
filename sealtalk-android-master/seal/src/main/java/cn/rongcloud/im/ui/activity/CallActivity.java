package cn.rongcloud.im.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.rongcloud.im.R;

/**
 * Created by kzhang3 on 8/8/2016.
 */

public class CallActivity extends BaseActivity {

    private EditText editText;
    private Button button_phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        initView();
        button_phone.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {


                //获取号码
//                String phone_Num = editText.getText().toString();
//
////定义执行打电话的意图对象
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone_Num));

                ActivityCompat.requestPermissions(CallActivity.this, new String[]{
                        Manifest.permission.CALL_PHONE
                }, 0x11);

//执行意图
//                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    Activity#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//
//                    // for Activity#requestPermissions for more details.
//                    startActivity(intent);
//
//                    Toast.makeText(CallActivity.this, "正在不能给" + phone_Num + "打电话", Toast.LENGTH_LONG).show();
//                    return;
//                }


//吐司的效果
//                Toast.makeText(CallActivity.this, "正在给" + phone_Num + "打电话", Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void initView() {
//获取按钮组件
        button_phone = (Button) findViewById(R.id.butphone);
//获取输入框组件
        editText = (EditText) findViewById(R.id.editview);

        Intent intent1 = getIntent();

        editText.setText(intent1.getStringExtra("callnumber"));


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x11) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.i("CMCC", "权限被允许");
                String phone_Num = editText.getText().toString();

//定义执行打电话的意图对象
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone_Num));

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            } else {
                Log.i("CMCC", "权限被拒绝");
            }
        }
    }

}
