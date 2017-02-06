package com.adev.swiftcontributor;

import android.app.Application;

import io.realm.Realm;

public class SwiftContributor extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }

}

