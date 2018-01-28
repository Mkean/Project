package app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * 作者：王庆
 * 时间：2017/12/7
 */

public class MyApp extends Application {
    private static boolean isLoginSuccess = false;
    private static Context context;
    private static Handler handler;
    private static int mainId;

    /**
     * 对外提供了context
     *
     * @return
     */
    public static Context getAppContext() {
        return context;
    }

    /**
     * 得到全局的handler
     *
     * @return
     */
    public static Handler getAppHanler() {
        return handler;
    }

    /**
     * 获取主线程id
     *
     * @return
     */
    public static int getMainThreadId() {
        return mainId;
    }

    public static boolean isLoginSuccess() {
        return isLoginSuccess;
    }

    public static void setIsLoginSuccess(boolean isLoginSuccess) {
        MyApp.isLoginSuccess = isLoginSuccess;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(configuration);
        Log.e("Application", isLoginSuccess + "");
        //关于context----http://blog.csdn.net/lmj623565791/article/details/40481055
        context = getApplicationContext();
        //初始化handler
        handler = new Handler();
        //主线程的id
        mainId = Process.myTid();
    }

}
