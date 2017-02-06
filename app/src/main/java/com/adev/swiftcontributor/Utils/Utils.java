package com.adev.swiftcontributor.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by yakko on 06/02/2017.
 */

public class Utils {

    /**
     * Get the online status
     *
     * @param context Context of the current Activity/Fragment
     * @return the state of the connection (active or not)
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
