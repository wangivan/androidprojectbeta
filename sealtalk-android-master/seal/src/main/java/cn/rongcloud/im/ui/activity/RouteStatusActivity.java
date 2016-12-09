package cn.rongcloud.im.ui.activity;

import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import cn.rongcloud.im.R;
import cn.rongcloud.im.ui.widget.RoundProgressBar;

/**
 * Created by Ivan.Wang on 2016/11/10.
 */

public class RouteStatusActivity extends BaseActivity {

    RoundProgressBar barStroke;
    RoundProgressBar barStrokeText;
    RoundProgressBar barFill;
    Timer updateTimer;
    TimerTask updateTimerTask;
    int currentValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_status_layout);

        barStroke = (RoundProgressBar) findViewById(R.id.barStroke);
        barStroke.setMax(100);

        barStrokeText = (RoundProgressBar) findViewById(R.id.barStrokeText);
        barStrokeText.setMax(100);

        //barFill = (RoundProgressBar) findViewById(R.id.barFill);
        //barFill.setMax(100);

        updateTimer = new Timer();

        updateTimerTask = new TimerTask() {
            @Override
            public void run() {
                currentValue += 1;
                barStroke.setValue(currentValue);
                barStrokeText.setValue(currentValue);
                // barFill.setValue(currentValue);

                if (currentValue >= 100)
                    currentValue = 0;
            }
        };
        updateTimer.schedule(updateTimerTask, 0, 50);
    }
}
