package app;

import android.app.Application;

import com.example.administrator.changhong.BuildConfig;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.xutils.x;

/**
 * Created by Administrator on 2016/7/13.
 */
public class ChangHongApp extends Application {
    /*private IWXAPI iwxapi;
    private static final String APP_ID="wx539256bab11005a7";*/
    @Override
    public void onCreate() {
        super.onCreate();
        /*//通过WXAPIFactory获取IWXAPI的实例
        iwxapi= WXAPIFactory.createWXAPI(getApplicationContext(),APP_ID,true);
        //将应用appid注册到微信
        iwxapi.registerApp(APP_ID);*/
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
