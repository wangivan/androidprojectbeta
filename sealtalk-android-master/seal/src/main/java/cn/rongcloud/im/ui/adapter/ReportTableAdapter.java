package cn.rongcloud.im.ui.adapter;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.rongcloud.im.R;
import cn.rongcloud.im.ui.activity.WorkShopChartActivity;

/**
 * Created by Ivan.Wang on 2016/11/27.
 */

public class ReportTableAdapter extends BaseAdapter {

    public final static String KEY= "WORKSHOP";
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public ReportTableAdapter(Context context, List<Map<String, Object>> data){
        this.context=context;
        this.data=data;
        this.layoutInflater= LayoutInflater.from(context);
    }

    public final class TextItem{
        public TextView timeslot;
        public TextView concrete_quantity;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextItem zujian=null;
        if(convertView==null){
            zujian=new TextItem();
            //获得组件，实例化组件
            convertView=layoutInflater.inflate(R.layout.activity_report_table_listview, null);
            zujian.timeslot=(TextView)convertView.findViewById(R.id.time_solt);
            zujian.timeslot.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            final TextItem finalZujian = zujian;
            zujian.timeslot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, ((TextView)v).getText(), Toast.LENGTH_SHORT).show();
//                    context.startActivity(new Intent());
                    Intent intent = new Intent(context,WorkShopChartActivity.class);
                    intent.putExtra(KEY, finalZujian.timeslot.getText().toString());
                    context.startActivity(intent);
                }
            });
            zujian.concrete_quantity = (TextView)convertView.findViewById(R.id.concrete_quantity);
            //zujian.info=(TextView)convertView.findViewById(R.id.info);
            convertView.setTag(zujian);
        }else{
            zujian=(TextItem)convertView.getTag();
        }
        //绑定数据
        zujian.timeslot.setText((String)data.get(position).get("time"));
        zujian.concrete_quantity.setText((String)(data.get(position).get("quantity")));
        //zujian.info.setText((String)data.get(position).get("info"));
        return convertView;
    }
}
