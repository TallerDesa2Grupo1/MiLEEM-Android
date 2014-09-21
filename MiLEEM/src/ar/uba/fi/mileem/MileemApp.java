package ar.uba.fi.mileem;

import android.app.Application;
import android.content.Context;

public class MileemApp extends Application {
    private static MileemApp instance;

    public static MileemApp getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
    }

    public void onCreate() {
        instance = this;
        super.onCreate();
    }
    
}