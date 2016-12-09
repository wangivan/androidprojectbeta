package cn.rongcloud.im.ui.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.rongcloud.im.R;

/**
 * Created by Ivan.Wang on 2016/11/24.
 */

public class FactoryReportFormsActivity extends BaseActivity {
    private WebView webview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory_report_forms);
        webview = (WebView) findViewById(R.id.webview);
        //设置WebView属性，能够执行Javascript脚本
        //支持javascript
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setUseWideViewPort(true);//关键点
        webview.getSettings().setAppCacheEnabled(true);

        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setJavaScriptEnabled(true); // 设置支持javascript脚本
        webview.getSettings().setAllowFileAccess(true); // 允许访问文件
        webview.getSettings().setBuiltInZoomControls(true); // 设置显示缩放按钮
        webview.getSettings().setSupportZoom(true); // 支持缩放
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.canGoBack();
        //加载需要显示的网页
        webview.loadUrl("http://xdf.i-plc.cn/static/report/other.html?add=1");
        //设置Web视图
        webview.setWebViewClient(new HelloWebViewClient ());
        //webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.far);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        //system.out.prinltn("maomao", "densityDpi = " + mDensity);
        if (mDensity == 240) {
            webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if(mDensity == 120) {
            webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){
            webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else if (mDensity == DisplayMetrics.DENSITY_TV){
            webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else{
            webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }
    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack(); //goBack()表示返回WebView的上一页面
        } else {
            finish();
        }
        return true;
    }

    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
