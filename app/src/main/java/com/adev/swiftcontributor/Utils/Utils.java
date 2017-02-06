package com.adev.swiftcontributor.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by yakko on 06/02/2017.
 */

public class Utils {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
