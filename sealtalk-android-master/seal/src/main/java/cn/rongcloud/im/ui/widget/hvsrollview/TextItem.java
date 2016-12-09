package cn.rongcloud.im.ui.widget.hvsrollview;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TextItem extends TextView implements OnClickListener{

	private Context context = null;
	public TextItem(Context context) {
		super(context);
		this.context = context;
		setOnClickListener(this);
	}
	
	public TextItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		new AlertDialog.Builder(context).setTitle("提示").setMessage("测试一下").setPositiveButton("确定", null)
		.setIcon(android.R.drawable.ic_dialog_alert).show();
	}

}
