package cn.rongcloud.im.ui.activity;



import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import cn.rongcloud.im.R;
import cn.rongcloud.im.ui.fragment.ElectricFragment;
import cn.rongcloud.im.ui.fragment.OutPutFragment;

/**
 * Created by Ivan.Wang on 2016/11/16.
 */

public class MyDeviceListActivity extends BaseActivity  {


    private ViewPager vPager = null;
    /**
     * 代表选项卡下的下划线的imageview
     */
    private ImageView cursor = null;

    private ImageView returnPrevious;
    /**
     * 选项卡下划线长度
     */
    private static int lineWidth = 0;

    /**
     * 偏移量
     *		 （手机屏幕宽度/3-选项卡长度）/2
     */
    private static int offset = 0;

    /**
     * 选项卡总数
     */
    private static final int TAB_COUNT = 2;
    /**
     * 当前显示的选项卡位置
     */
    private int current_index = 0;

    /**
     * 选项卡标题
     */
    private TextView text1,text2;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.layout_tab_fragment_reportchart);

        vPager = (ViewPager) findViewById(R.id.vPager);


        initImageView();
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        final TextView[] titles = {text1,text2};
        titles[0].setTextColor(Color.RED);
//        final List<Fragment> mFragment = new ArrayList<>();
//        mFragment.add(ElectricFragment.getInstance());
//        mFragment.add(ElectricFragment.getInstance());
        vPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public int getCount()
            {
                return TAB_COUNT;
            }

            @Override
            public Fragment getItem(int index)//直接创建fragment对象并返回
            {
//                return mFragment.get(index);
                switch (index)
                {
                    case 0:
                        return new ElectricFragment();
                    case 1:
                        return new OutPutFragment();
                }
                return null;
            }
        });
        vPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            int one = offset * 2 + lineWidth;// 页卡1 -> 页卡2 偏移量
            @Override
            public void onPageSelected(int index)//设置标题的颜色以及下划线的移动效果
            {
                Animation animation = new TranslateAnimation(one*current_index,one*index, 0,0);
                animation.setFillAfter(true);
                animation.setDuration(300);
                cursor.startAnimation(animation);
                titles[current_index].setTextColor(Color.BLACK);
                titles[index].setTextColor(Color.RED);
                current_index = index;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
            }

            @Override
            public void onPageScrollStateChanged(int index)
            {
            }
        });
    }
    private void initImageView()
    {
        cursor = (ImageView) findViewById(R.id.cursor);
        //获取图片宽度
//        lineWidth = BitmapFactory.decodeResource(getResources(),R.drawable.line).getWidth();
        lineWidth = 2;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //获取屏幕宽度
        int screenWidth = dm.widthPixels;
        Matrix matrix = new Matrix();
        offset = (int) ((screenWidth/(float)TAB_COUNT - lineWidth)/2);
        matrix.postTranslate(offset, 0);
        //设置初始位置
        cursor.setImageMatrix(matrix);
//        returnPrevious = (ImageView) findViewById(R.id.return_previous);
//        returnPrevious.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MyDeviceListActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
    }

//    private long exitTime = 0;
//
//    @Override
//    public void onBackPressed() {
//        if(System.currentTimeMillis() - exitTime > 2000) {
//            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//            exitTime = System.currentTimeMillis();
//        } else {
//            finish();
//            System.exit(0);
//            android.os.Process.killProcess(android.os.Process.myPid());
//        }
//    }




}
