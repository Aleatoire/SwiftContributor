package com.adev.swiftcontributor;

        import android.app.Application;

        import io.realm.Realm;

/**
 * Created by yakko on 05/02/2017.
 */

public class SwiftContributor extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }

}

