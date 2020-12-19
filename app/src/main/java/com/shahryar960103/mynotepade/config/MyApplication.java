package com.shahryar960103.mynotepade.config;

import android.app.Application;
import android.content.Context;



public class MyApplication extends Application {


    public static MyApplication instance = null;

    public MyApplication getInstance(){
        return instance;
    }

    public Context getContext(){
        return instance;
    }


   /* public static DrawerToggleComponent getDrawerToggleComponent(){

        DrawerToggleComponent drawerToggleComponent = DaggerDrawerToggleComponent.builder()
                .drawerToggleModule(new DrawerToggleModule()).build();

    }*/



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
