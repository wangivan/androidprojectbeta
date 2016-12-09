package cn.rongcloud.im.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.rongcloud.im.R;
import cn.rongcloud.im.model.Message;
import cn.rongcloud.im.ui.adapter.ReportTableAdapter;
import cn.rongcloud.im.utils.DateTimePickDialogUtil;

/**
 * Created by kzhang3 on 12/6/2016.
 */

public class DayReportSearchCheckBoxinListViewActivity extends BaseActivity {
    /** Called when the activity is first created. */
    private MyAdapter adapter;
    private ListView listview;
    private Button checkAll;
    private Button noCheckAll;
    private EditText startDateTime;
    private Button searchData;
    int count;
//    private ArrayList<Message> list= new ArrayList<Message>();
    //private EditText endDateTime;

    private String initStartDateTime; // 初始化开始时间
    //private String initEndDateTime = ""; // 初始化结束时间
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayreport_checkboxlist);



        searchData = (Button) findViewById(R.id.SearchData);
        //dataPicker 代码

        SimpleDateFormat    formatter    =   new SimpleDateFormat("yyyy年MM月dd日");
        Date    curDate    =   new Date(System.currentTimeMillis());//获取当前时间
        String    str    =    formatter.format(curDate);
        System.out.print(str);

        initStartDateTime = str;

        startDateTime = (EditText) findViewById(R.id.inputDate);
        //endDateTime = (EditText) findViewById(R.id.inputDate2);

        startDateTime.setText(initStartDateTime);
        //endDateTime.setText(initEndDateTime);

        startDateTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        DayReportSearchCheckBoxinListViewActivity.this, initStartDateTime);
                dateTimePicKDialog.dateTimePicKDialog(startDateTime);

            }
        });

        //End

        listview = (ListView)findViewById(R.id.listview);
        checkAll = (Button)findViewById(R.id.button1);
        noCheckAll = (Button)findViewById(R.id.button2);

        Intent intent = getIntent();
        final String label = intent.getStringExtra("LABEL");
        ArrayList<String> itemList = intent.getStringArrayListExtra("DEVICELIST");
        final String type = intent.getStringExtra("TYPE");
        TextView tv = (TextView)findViewById(R.id.factoryname);
        tv.setText(label+"("+type+")");
        adapter = new MyAdapter(itemList);
        listview.setAdapter(adapter);

        //查询数据按钮
        searchData.setOnClickListener(new Button.OnClickListener(){


            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(),"请选择查询时间",Toast.LENGTH_SHORT).show();
                if(startDateTime.getText().length()==0|startDateTime.getText().equals(null)||startDateTime.getText()==null){

                    Toast.makeText(getApplicationContext(),"请选择查询时间",Toast.LENGTH_SHORT).show();
                    return;
                }
                for(int i = 0;i < adapter.getCount();i++){
                    Message msg = (Message)adapter.getItem(i);
                    //Toast.makeText(getApplicationContext(),String.valueOf(msg.isCheck),Toast.LENGTH_SHORT).show();
                    if (String.valueOf(msg.isCheck).equals("false")){
//                        Toast.makeText(getApplicationContext(),"CA1",Toast.LENGTH_SHORT).show();
                        count+=1;
                    }
                }
//                Toast.makeText(getApplicationContext(),"count: "+count+" listSize: "+adapter.getCount(),Toast.LENGTH_SHORT).show();

                if(count==adapter.getCount()){

                    Toast.makeText(getApplicationContext(),"请至少选择一台机器",Toast.LENGTH_SHORT).show();
                    count=0;
                    return;
                }

                Intent intent = new Intent(DayReportSearchCheckBoxinListViewActivity.this,DetailDeviceMonthReportActivity.class);
                intent.putExtra("TYPE",type);
                intent.putStringArrayListExtra("DEVICELIST",adapter.getSelectedList());
                intent.putExtra("TIME",startDateTime.getText().toString());
                intent.putExtra("WORKSHOP",label);
                startActivity(intent);
            }
        });

        //End


        checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.checkAll();
            }
        });
        noCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.noCheckAll();
            }
        });
    }
    private class MyAdapter extends BaseAdapter{

        private ArrayList<Message> list= new ArrayList<Message>();
        private ArrayList<String> selectedList = new ArrayList<String>();
        public MyAdapter(List<String> devicelist){
            for (String element : devicelist) {
                list.add(new Message(element));
            }
        }
        public void checkAll(){
            selectedList.clear();
            for(Message msg:list){
                msg.isCheck = true;
                selectedList.add(msg.str);
            }
            notifyDataSetChanged();
        }
        public void noCheckAll(){
            selectedList.clear();
            for(Message msg:list){
                msg.isCheck = false;
            }
            notifyDataSetChanged();
        }

        public ArrayList<String> getSelectedList() {
            return selectedList;
        }
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public Object getItem(int position) {
            return list.get(position);
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(DayReportSearchCheckBoxinListViewActivity.this);
                convertView = inflater.inflate(R.layout.dayreport_listview_item, null);
                viewHolder = new ViewHolder();
                viewHolder.checkBox = (CheckBox)convertView.findViewById(R.id.checkBox1);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            final Message msg = list.get(position);
            viewHolder.checkBox.setText(msg.str);
            viewHolder.checkBox.setChecked(msg.isCheck);
//注意这里设置的不是onCheckedChangListener，还是值得思考一下的
            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(msg.isCheck){
                        msg.isCheck = false;
                        selectedList.remove(msg.str);
                    }else{
                        msg.isCheck = true;
                        selectedList.add(msg.str);
                    }
                    count =0;
                }
            });
            return convertView;
        }
    }
    private class ViewHolder{
        CheckBox checkBox;
    }
}
