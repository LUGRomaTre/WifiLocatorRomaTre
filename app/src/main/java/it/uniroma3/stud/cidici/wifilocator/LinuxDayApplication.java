package it.uniroma3.stud.cidici.wifilocator;

import android.app.Application;
import android.content.Context;

public class LinuxDayApplication extends Application {
    private static LinuxDayApplication mInstance;
    private static Context mAppContext;

    public static LinuxDayApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public void setAppContext(Context mAppContext) {
        LinuxDayApplication.mAppContext = mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        this.setAppContext(getApplicationContext());
    }
}