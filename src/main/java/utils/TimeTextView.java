package utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * 作者：王庆
 * 时间：2018/1/11
 */

public class TimeTextView extends TextView implements Runnable {
    private String[] times;
    private long mhour, mmin, msecond;//天，小时，分钟，秒
    private boolean run = false; //是否启动了

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TimeTextView(Context context) {
        super(context);
    }

    public String[] getTimes() {
        return times;
    }

    public void setTimes(String[] times) {
        this.times = times;
        mhour = Long.parseLong(times[0].trim());
        mmin = Long.parseLong(times[1].trim());
        msecond = Long.parseLong(times[2].trim());
    }

    /**
     * 倒计时计算
     */
    private void ComputeTime() {
        msecond--;
        if (msecond < 0) {
            msecond = 59;
            mmin--;
            if (mmin < 0) {
                mmin = 59;
                mhour--;
                if (mhour < 0) {
                    // 倒计时结束
                    mhour = 0;
                }
            }
        }
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    private String get2Num(long l) {
        if (l < 10) {
            return "0" + l;
        }
        return String.valueOf(l);
    }

    @Override
    public void run() {
        //标示已经启动
        removeCallbacks(this);
        if (!isRun()) {
            return;
        }
        ComputeTime();
        Log.i("wj", "TextTimer正在运行");
        String strTime = get2Num(mhour) + ":" + get2Num(mmin) + ":" + get2Num(msecond);
        int bStart = strTime.indexOf(get2Num(mhour));
        int bend = bStart + get2Num(mhour).length();
        Log.e("TAG", bStart + ":" + bend);
        int fStart = strTime.indexOf(get2Num(mmin));
        int fend = fStart + get2Num(mmin).length();
        Log.e("TAG", fStart + "::" + fend);
        int start = strTime.indexOf(get2Num(msecond));
        int end = start + get2Num(msecond).length();
        SpannableStringBuilder style = new SpannableStringBuilder(strTime);
        style.setSpan(new BackgroundColorSpan(Color.RED), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new BackgroundColorSpan(Color.RED), 3, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new BackgroundColorSpan(Color.RED), 6, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(Color.RED), bend, bend + ":".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(Color.RED), fend, fend + ":".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        this.setText(style);
        postDelayed(this, 1000);
    }
}
