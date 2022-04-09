package com.cygen.cygendineinpos.Printer;

import android.app.Application;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();

    }

    /**
     * Connect print service through interface library
     */
    private void init(){
        SunmiPrintHelper.getInstance().initSunmiPrinterService(this);
    }
}
